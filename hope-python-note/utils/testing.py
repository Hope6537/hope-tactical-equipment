# encoding: utf-8

"""Unit testing helpers for asynchronous marrow.io IOLoop and IOStream.

This is a barely-modified version of the unit testing rig from Tornado.
"""

import os
import sys
import time
import marrow.io.ioloop
import unittest
import contextlib

from marrow.io.stack_context import StackContext, NullContext
from marrow.io.iostream import IOStream


log = __import__('logging').getLogger(__name__)
__all__ = ['AsyncTestCase']



class AsyncTestCase(unittest.TestCase):
    def __init__(self, *args, **kwargs):
        super(AsyncTestCase, self).__init__(*args, **kwargs)
        self.__stopped = False
        self.__running = False
        self.__failure = None
        self.__stop_args = None
        self.io_loop = None
    
    def setUp(self):
        super(AsyncTestCase, self).setUp()
        self.io_loop = marrow.io.ioloop.IOLoop()
    
    def tearDown(self):
        if self.io_loop is not marrow.io.ioloop.IOLoop.instance():
            # Try to clean up any file descriptors left open in the ioloop.
            # This avoids leaks, especially when tests are run repeatedly
            # in the same process with autoreload (because curl does not
            # set FD_CLOEXEC on its file descriptors)
            for fd in self.io_loop._handlers.keys():
                if (fd == self.io_loop._waker_reader.fileno() or
                    fd == self.io_loop._waker_writer.fileno()):
                    # Close these through the file objects that wrap
                    # them, or else the destructor will try to close
                    # them later and log a warning
                    continue
                try:
                    os.close(fd)
                except:
                    log.debug("error closing fd %d", fd, exc_info=True)
            
            self.io_loop._waker_reader.close()
            self.io_loop._waker_writer.close()
    
    def stop(self, _arg=None, **kwargs):
        '''Stops the ioloop, causing one pending (or future) call to wait()
        to return.
        
        Keyword arguments or a single positional argument passed to stop() are
        saved and will be returned by wait().
        '''
        assert _arg is None or not kwargs
        self.__stop_args = kwargs or _arg
        
        if self.__running:
            self.io_loop.stop()
            self.__running = False
        
        self.__stopped = True
    
    def wait(self, condition=None, timeout=5):
        """Runs the IOLoop until stop is called or timeout has passed.
        
        In the event of a timeout, an exception will be thrown.
        
        If condition is not None, the IOLoop will be restarted after stop()
        until condition() returns true.
        """
        if not self.__stopped:
            if timeout:
                def timeout_func():
                    try:
                        raise self.failureException('Async operation timed out after %d seconds.' % timeout)
                    
                    except:
                        self.__failure = sys.exc_info()
                    
                    self.stop()
                
                self.io_loop.add_timeout(time.time() + timeout, timeout_func)
            
            while True:
                self.__running = True
                
                with NullContext():
                    # Wipe out the StackContext that was established in
                    # self.run() so that all callbacks executed inside the
                    # IOLoop will re-run it.
                    self.io_loop.start()
                
                if (self.__failure is not None or condition is None or condition()):
                    break
            
        assert self.__stopped
        self.__stopped = False
        
        if self.__failure is not None:
            raise self.__failure[1]
            
        result = self.__stop_args
        self.__stop_args = None
        
        return result
    
    @contextlib.contextmanager
    def _stack_context(self):
        try:
            yield
        except:
            self.__failure = sys.exc_info()
            log.exception("Error within stack context.")
            self.stop()
    
    def run(self, result=None):
        with StackContext(self._stack_context):
            super(AsyncTestCase, self).run(result)
