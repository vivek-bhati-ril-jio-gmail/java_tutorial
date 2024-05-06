package MultiThreading;

public class SynchronizedBlock {

    private volatile int i;

    public void inc() {
        synchronized (this) {
            i++;
        }
    }

    public void dec() {
        synchronized (this) {
            i--;
        }
    }

    public int getI() {
        return i;
    }

    public static void main(String[] args) {
        SynchronizedBlock synchronizedBlock = new SynchronizedBlock();

        // MultiThreading.AtomicExample increment
        synchronizedBlock.inc();

        // MultiThreading.AtomicExample decrement
        synchronizedBlock.dec();

        // MultiThreading.AtomicExample reading
        System.out.println(synchronizedBlock.getI());
    }
}
