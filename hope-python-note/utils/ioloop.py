# encoding: utf-8
#
# Copyright 2009 Facebook
# Portions Copyright 2010-2011 Alice Bevan-McGregor
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may
# not use this file except in compliance with the License. You may obtain
# a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations
# under the License.

"""A level-triggered I/O loop for non-blocking sockets."""

from __future__ import unicode_literals

import os
import errno
import select

from bisect import insort
from time import time
from traceback import format_stack

from marrow.util.compat import exception
from marrow.io import stack_context

try:
    import signal

except ImportError:
    signal = None

try:
    import fcntl

except ImportError:
    if os.name == 'nt':
        from marrow.io import win32_support
        from marrow.io import win32_support as fcntl
    
    else:
        raise


log = __import__('logging').getLogger(__name__)
__all__ = ['IOLoop', 'PeriodicCallback']



class IOLoop(object):
    """A level-triggered I/O loop.
    
    We use epoll if it is available, or else we fall back on select(). If
    you are implementing a system that needs to handle 1000s of simultaneous
    connections, you should use Linux and either compile our epoll module or
    use Python 2.6+ to get epoll support.
    
    Example usage for a simple TCP server:
    
        import errno
        import functools
        import ioloop
        import socket
        from marrow.util.compat import exception
        
        def connection_ready(sock, fd, events):
            while True:
                try:
                    connection, address = sock.accept()
                except socket.error:
                    exc = exception().exception
                    if exc.attr[0] not in (errno.EWOULDBLOCK, errno.EAGAIN):
                        raise
                    return
                connection.setblocking(0)
                handle_connection(connection, address)
        
        sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM, 0)
        sock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        sock.setblocking(0)
        sock.bind(("", port))
        sock.listen(128)
        
        io_loop = ioloop.IOLoop.instance()
        callback = functools.partial(connection_ready, sock)
        io_loop.add_handler(sock.fileno(), callback, io_loop.READ)
        io_loop.start()
    
    """
    
    # Constants from the epoll module
    _EPOLLIN = 0x001
    _EPOLLPRI = 0x002
    _EPOLLOUT = 0x004
    _EPOLLERR = 0x008
    _EPOLLHUP = 0x010
    _EPOLLRDHUP = 0x2000
    _EPOLLONESHOT = (1 << 30)
    _EPOLLET = (1 << 31)
    
    # Our events map exactly to the epoll events
    NONE = 0
    READ = _EPOLLIN
    WRITE = _EPOLLOUT
    ERROR = _EPOLLERR | _EPOLLHUP | _EPOLLRDHUP
    
    def __init__(self, impl=None):
        log.debug("IOLoop reactor initializing.")
        
        self._impl = impl or _poll()
        if hasattr(self._impl, 'fileno'):
            self._set_close_exec(self._impl.fileno())
        self._handlers = {}
        self._events = {}
        self._callbacks = set()
        self._timeouts = []
        self._running = False
        self._stopped = False
        self._blocking_log_threshold = None
        
        # Create a pipe that we send bogus data to when we want to wake
        # the I/O loop when it is idle
        if os.name != 'nt':
            r, w = os.pipe()
            self._set_nonblocking(r)
            self._set_nonblocking(w)
            self._set_close_exec(r)
            self._set_close_exec(w)
            self._waker_reader = os.fdopen(r, "rb", 0)
            self._waker_writer = os.fdopen(w, "wb", 0)
        else:
            self._waker_reader = self._waker_writer = win32_support.Pipe()
            r = self._waker_writer.reader_fd
        
        self.add_handler(r, self._read_waker, self.READ)
    
    @classmethod
    def instance(cls):
        """Returns a global IOLoop instance.
        
        Most single-threaded applications have a single, global IOLoop.
        Use this method instead of passing around IOLoop instances
        throughout your code.
        
        A common pattern for classes that depend on IOLoops is to use
        a default argument to enable programs with multiple IOLoops
        but not require the argument for simpler applications:
        
            class MyClass(object):
                def __init__(self, io_loop=None):
                    self.io_loop = io_loop or IOLoop.instance()
        """
        # log.debug("Returning IOLoop singleton.")
        
        if not hasattr(cls, "_instance"):
            cls._instance = cls()
        return cls._instance
    
    @classmethod
    def initialized(cls):
        return hasattr(cls, "_instance")
    
    def add_handler(self, fd, handler, events):
        """Registers the given handler to receive the given events for fd."""
        # log.debug("Registering handler %r on %d.", handler, fd)
        self._handlers[fd] = stack_context.wrap(handler)
        self._impl.register(fd, events | self.ERROR)
    
    def update_handler(self, fd, events):
        """Changes the events we listen for fd."""
        # log.debug("Updating handler events on %d.", fd)
        self._impl.modify(fd, events | self.ERROR)
    
    def remove_handler(self, fd):
        """Stop listening for events on fd."""
        # log.debug("Removing handlers from %d.", fd)
        
        self._handlers.pop(fd, None)
        self._events.pop(fd, None)
        
        try:
            self._impl.unregister(fd)
        
        except (OSError, IOError):
            log.warn("Error deleting fd from IOLoop.", exc_info=True)
    
    def set_blocking_log_threshold(self, s):
        """Logs a stack trace if the ioloop is blocked for more than s seconds.
        Pass None to disable.  Requires python 2.6 on a unixy platform.
        """
        if not hasattr(signal, "setitimer"):
            log.error("set_blocking_log_threshold requires a signal module with the setitimer method")
            return
        
        # log.debug("Setting blocking threshhold to %r.", s)
        
        self._blocking_log_threshold = s
        if s is not None:
            signal.signal(signal.SIGALRM, self._handle_alarm)
    
    def _handle_alarm(self, signal, frame):
        log.warning('IOLoop blocked for %f seconds in\n%s', self._blocking_log_threshold, ''.join(traceback.format_stack(frame)))
    
    def start(self):
        """Starts the I/O loop.
        
        The loop will run until one of the I/O handlers calls stop(), which
        will make the loop stop after the current event iteration completes.
        """
        log.debug("IOLoop reactor starting.")
        
        if self._stopped:
            self._stopped = False
            return
        
        self._running = True
        
        while True:
            # log.debug("Starting reactor tick.")
            
            # Never use an infinite timeout here - it can stall epoll
            poll_timeout = 0.2
            
            # Prevent IO event starvation by delaying new callbacks
            # to the next iteration of the event loop.
            callbacks = list(self._callbacks)
            for callback in callbacks:
                # A callback can add or remove other callbacks
                if callback in self._callbacks:
                    self._callbacks.remove(callback)
                    self._run_callback(callback)
            
            if self._callbacks:
                # log.debug("Callbacks waiting, reducing timeout.")
                poll_timeout = 0.0
            
            if self._timeouts:
                # log.debug("Timeouts pending.")
                
                now = time()
                while self._timeouts and self._timeouts[0].deadline <= now:
                    timeout = self._timeouts.pop(0)
                    self._run_callback(timeout.callback)
                
                if self._timeouts:
                    milliseconds = self._timeouts[0].deadline - now
                    poll_timeout = min(milliseconds, poll_timeout)
            
            if not self._running:
                log.debug("IOLoop reactor exiting.")
                break
            
            if self._blocking_log_threshold is not None:
                # clear alarm so it doesn't fire while poll is waiting for events.
                signal.setitimer(signal.ITIMER_REAL, 0, 0)
            
            try:
                event_pairs = self._impl.poll(poll_timeout)
            
            except Exception:
                exc = exception().exception
                # Depending on python version and IOLoop implementation,
                # different exception types may be thrown and there are
                # two ways EINTR might be signaled:
                # * e.errno == errno.EINTR
                # * e.args is like (errno.EINTR, 'Interrupted system call')
                # TODO: Determine if this can be simplified to:
                # if e.args == (4, "Interrupted system call"):
                if (getattr(exc, 'errno', None) == errno.EINTR or
                    (isinstance(getattr(exc, 'args'), tuple) and
                     len(exc.args) == 2 and exc.args[0] == errno.EINTR)):
                    log.warning("Interrupted system call", exc_info=True)
                    continue
                
                else:
                    log.exception("Error polling.")
                    raise
            
            if self._blocking_log_threshold is not None:
                signal.setitimer(signal.ITIMER_REAL, self._blocking_log_threshold, 0)
            
            # Pop one fd at a time from the set of pending fds and run
            # its handler. Since that handler may perform actions on
            # other file descriptors, there may be reentrant calls to
            # this IOLoop that update self._events
            self._events.update(event_pairs)
            while self._events:
                fd, events = self._events.popitem()
                
                try:
                    self._handlers[fd](fd, events)
                
                except (KeyboardInterrupt, SystemExit):
                    raise
                
                except (OSError, IOError):
                    exc = exception().exception
                    if exc.args[0] == errno.EPIPE:
                        # Happens when the client closes the connection
                        pass
                    
                    else:
                        log.error("Exception in I/O handler for fd %d", fd, exc_info=True)
                
                except:
                    log.error("Exception in I/O handler for fd %d", fd, exc_info=True)
        
        # reset the stopped flag so another start/stop pair can be issued
        self._stopped = False
        
        if self._blocking_log_threshold is not None:
            signal.setitimer(signal.ITIMER_REAL, 0, 0)
    
    def stop(self):
        """Stop the loop after the current event loop iteration is complete.
        If the event loop is not currently running, the next call to start()
        will return immediately.
        
        To use asynchronous methods from otherwise-synchronous code (such as
        unit tests), you can start and stop the event loop like this:
          ioloop = IOLoop()
          async_method(ioloop=ioloop, callback=ioloop.stop)
          ioloop.start()
        ioloop.start() will return after async_method has run its callback,
        whether that callback was invoked before or after ioloop.start.
        """
        log.debug("IOLoop reactor stopping.")
        self._running = False
        self._stopped = True
        self._wake()
    
    def running(self):
        """Returns true if this IOLoop is currently running."""
        return self._running
    
    def add_timeout(self, deadline, callback):
        """Calls the given callback at the time deadline from the I/O loop."""
        # log.debug("Adding timeout of %r using %r.", deadline, callback)
        timeout = _Timeout(deadline, stack_context.wrap(callback))
        insort(self._timeouts, timeout)
        return timeout
    
    def remove_timeout(self, timeout):
        # log.debug("Removing %r timeout.", timeout)
        self._timeouts.remove(timeout)
    
    def add_callback(self, callback):
        """Calls the given callback on the next I/O loop iteration."""
        # log.debug("Adding %r callback.", callback)
        self._callbacks.add(stack_context.wrap(callback))
        self._wake()
    
    def _wake(self):
        # log.debug("Waking up.")
        
        try:
            self._waker_writer.write(b"x")
        
        except IOError:
            pass
    
    def _run_callback(self, callback):
        # log.debug("Executing %r callback.", callback)
        
        try:
            callback()
        
        except (KeyboardInterrupt, SystemExit):
            raise
        
        except:
            self.handle_callback_exception(callback)
    
    def handle_callback_exception(self, callback):
        """This method is called whenever a callback run by the IOLoop
        throws an exception.
        
        By default simply logs the exception as an error.  Subclasses
        may override this method to customize reporting of exceptions.
        
        The exception itself is not passed explicitly, but is available
        in sys.exc_info.
        """
        log.exception("Exception in callback %r.", callback)
    
    def _read_waker(self, fd, events):
        # log.debug("Reading waker pipe.")
        
        try:
            while self._waker_reader.read():
                pass
                # log.debug("Read hunk.")
        
        except IOError:
            pass
    
    def _set_nonblocking(self, fd):
        # log.debug("Setting %d to non-blocking state.", fd)
        flags = fcntl.fcntl(fd, fcntl.F_GETFL)
        fcntl.fcntl(fd, fcntl.F_SETFL, flags | os.O_NONBLOCK)
    
    def _set_close_exec(self, fd):
        # log.debug("Setting CLOEXEC on %d.", fd)
        flags = fcntl.fcntl(fd, fcntl.F_GETFD)
        fcntl.fcntl(fd, fcntl.F_SETFD, flags | fcntl.FD_CLOEXEC)


class _Timeout(object):
    """An IOLoop timeout, a UNIX timestamp and a callback"""
    
    # Reduce memory overhead when there are lots of pending callbacks
    __slots__ = ['deadline', 'callback']
    
    def __init__(self, deadline, callback):
        # log.debug("Initializing timeout with %r deadline, %r callback.", deadline, callback)
        self.deadline = deadline
        self.callback = callback
    
    def __cmp__(self, other):
        # log.debug("Comparing timeouts.")
        return cmp((self.deadline, id(self.callback)), (other.deadline, id(other.callback)))
    
    def __lt__(self, other):
        # log.debug("Comparing timeout less-than.")
        return (self.deadline, id(self.callback)) < (other.deadline, id(other.callback))


class PeriodicCallback(object):
    """Schedules the given callback to be called periodically.
    
    The callback is called every callback_time milliseconds.
    """
    def __init__(self, callback, callback_time, io_loop=None):
        # log.debug("Adding %r callback, with %r period, to %r io_loop.", callback, callback_time, io_loop)
        self.callback = callback
        self.callback_time = callback_time
        self.io_loop = io_loop or IOLoop.instance()
        self._running = False
    
    def start(self):
        # log.debug("Starting periodic callback.")
        self._running = True
        timeout = time() + self.callback_time / 1000.0
        self.io_loop.add_timeout(timeout, self._run)
    
    def stop(self):
        # log.debug("Stopping periodic callback.")
        self._running = False
    
    def _run(self):
        if not self._running: return
        
        # log.debug("Executing periodic callback.")
        
        try:
            self.callback()
        
        except (KeyboardInterrupt, SystemExit):
            raise
        
        except:
            log.error("Error in periodic callback", exc_info=True)
        
        self.start()


class _KQueue(object):
    """A kqueue-based event loop for BSD/Mac systems."""
    def __init__(self):
        self._kqueue = select.kqueue()
        self._active = {}
    
    def fileno(self):
        return self._kqueue.fileno()
    
    def register(self, fd, events):
        self._control(fd, events, select.KQ_EV_ADD)
        self._active[fd] = events
    
    def modify(self, fd, events):
        self.unregister(fd)
        self.register(fd, events)
    
    def unregister(self, fd):
        events = self._active.pop(fd)
        self._control(fd, events, select.KQ_EV_DELETE)
    
    def _control(self, fd, events, flags):
        kevents = []
        if events & IOLoop.WRITE:
            kevents.append(select.kevent(
                    fd, filter=select.KQ_FILTER_WRITE, flags=flags))
        if events & IOLoop.READ or not kevents:
            # Always read when there is not a write
            kevents.append(select.kevent(
                    fd, filter=select.KQ_FILTER_READ, flags=flags))
        # Even though control() takes a list, it seems to return EINVAL
        # on Mac OS X (10.6) when there is more than one event in the list.
        for kevent in kevents:
            self._kqueue.control([kevent], 0)
    
    def poll(self, timeout):
        kevents = self._kqueue.control(None, 1000, timeout)
        events = {}
        for kevent in kevents:
            fd = kevent.ident
            flags = 0
            if kevent.filter == select.KQ_FILTER_READ:
                events[fd] = events.get(fd, 0) | IOLoop.READ
            if kevent.filter == select.KQ_FILTER_WRITE:
                events[fd] = events.get(fd, 0) | IOLoop.WRITE
            if kevent.flags & select.KQ_EV_ERROR:
                events[fd] = events.get(fd, 0) | IOLoop.ERROR
        return list(events.items())


class _Select(object):
    """A simple, select()-based IOLoop implementation for non-Linux systems"""
    def __init__(self):
        self.read_fds = set()
        self.write_fds = set()
        self.error_fds = set()
        self.fd_sets = (self.read_fds, self.write_fds, self.error_fds)

    def register(self, fd, events):
        if events & IOLoop.READ: self.read_fds.add(fd)
        if events & IOLoop.WRITE: self.write_fds.add(fd)
        if events & IOLoop.ERROR: self.error_fds.add(fd)

    def modify(self, fd, events):
        self.unregister(fd)
        self.register(fd, events)

    def unregister(self, fd):
        self.read_fds.discard(fd)
        self.write_fds.discard(fd)
        self.error_fds.discard(fd)

    def poll(self, timeout):
        readable, writeable, errors = select.select(
            self.read_fds, self.write_fds, self.error_fds, timeout)
        events = {}
        for fd in readable:
            events[fd] = events.get(fd, 0) | IOLoop.READ
        for fd in writeable:
            events[fd] = events.get(fd, 0) | IOLoop.WRITE
        for fd in errors:
            events[fd] = events.get(fd, 0) | IOLoop.ERROR
        return list(events.items())


# Choose a poll implementation. Use epoll if it is available, fall back to
# select() for non-Linux platforms

try:
    _poll = select.epoll # Python 2.6+ on Linux.

except AttributeError:
    try:
        _poll = select.KQueue # Python 2.6+ on BSD or Mac.
    
    except AttributeError:
        _poll = _Select # All other systems.
    
    else:
        _poll = _KQueue
