package MultiThreading;

public class SynchronizedMethod {

    private volatile int i;

    public synchronized void inc() {
        i++;
    }

    public synchronized void dec() {
        i--;
    }

    public int getI() {
        return i;
    }

    public static void main(String[] args) {
        SynchronizedMethod synchronizedMethod = new SynchronizedMethod();

        // MultiThreading.AtomicExample increment
        synchronizedMethod.inc();

        // MultiThreading.AtomicExample decrement
        synchronizedMethod.dec();

        // MultiThreading.AtomicExample reading
        System.out.println(synchronizedMethod.getI());
    }
}
