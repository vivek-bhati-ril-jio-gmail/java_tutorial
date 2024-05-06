import java.util.concurrent.locks.Lock;

public class ReentrantLock<T> {

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
        ReentrantLock<Integer> reentrantLock = new ReentrantLock<>();

        // Atomic modification with lock/unlock
        reentrantLock.setWithLock(1);

        // Atomic modification with trying lock/unlock
        reentrantLock.setWithTryLock(2);

        // Atomic modification with interruptibly lock/unlock
        reentrantLock.setWithLockInterruptibly(3);
    }
}
