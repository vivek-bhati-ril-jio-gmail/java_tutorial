package MultiThreading;

import java.util.concurrent.locks.Lock;

public class ReentrantLockExample<T> {

    private final Lock lock = new java.util.concurrent.locks.ReentrantLock();
    private T data;

    public void setWithLock(T data) {
        try {
            lock.lock();
            this.data = data;
        } finally {
            lock.unlock();
        }
    }

    public void setWithTryLock(T data) {
        try {
            if (lock.tryLock()) {
                this.data = data;
            }
        } finally {
            lock.unlock();
        }
    }

    public void setWithLockInterruptibly(T data) {
        try {
            lock.lockInterruptibly();
            this.data = data;
        } catch (InterruptedException e) {
            System.err.println("InterruptedException:" + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockExample<Integer> reentrantLockExample = new ReentrantLockExample<>();

        // MultiThreading.AtomicExample modification with lock/unlock
        reentrantLockExample.setWithLock(1);

        // MultiThreading.AtomicExample modification with trying lock/unlock
        reentrantLockExample.setWithTryLock(2);

        // MultiThreading.AtomicExample modification with interruptibly lock/unlock
        reentrantLockExample.setWithLockInterruptibly(3);
    }
}
