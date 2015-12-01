package org.hope6537.security;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * One Shot Latch
 * Created by hope6537 on 15/11/27.
 * Any Question sent to hope6537@qq.com
 */
public class OneShotLatch {

    private final Sync sync = new Sync();

    public void singal() {
        sync.releaseShared(0);
    }

    public void await() throws InterruptedException {
        sync.acquireInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        /**
         * if the latch is open ,will success ,else fail
         *
         * @param arg
         * @return
         */
        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 1 ? 1 : -1;
        }

        /**
         * open the latch , and let the next acquires pass
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryReleaseShared(int arg) {
            setState(1);
            return true;
        }
    }
}
