import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock<T> {

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
        ReadWriteLock<Integer> readWriteLock = new ReadWriteLock<>();

        // Atomic modification with write lock
        readWriteLock.setWithWriteLock(1);

        // Atomic reading with read lock
        System.out.println(readWriteLock.getWithReadLock());
    }

}
