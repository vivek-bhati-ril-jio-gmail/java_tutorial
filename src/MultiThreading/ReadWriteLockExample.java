package MultiThreading;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample<T> {

    private final java.util.concurrent.locks.ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private T data;

    @SuppressWarnings("SameParameterValue")
    private void setWithWriteLock(T data) {
        try {
            writeLock.lock();
            this.data = data;
        } finally {
            writeLock.unlock();
        }
    }

    private T getWithReadLock() {
        try {
            readLock.lock();
            return data;
        } finally {
            readLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockExample<Integer> readWriteLockExample = new ReadWriteLockExample<>();

        // MultiThreading.AtomicExample modification with write lock
        readWriteLockExample.setWithWriteLock(1);

        // MultiThreading.AtomicExample reading with read lock
        System.out.println(readWriteLockExample.getWithReadLock());
    }

}
