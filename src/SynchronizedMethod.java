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

        // Atomic increment
        synchronizedMethod.inc();

        // Atomic decrement
        synchronizedMethod.dec();

        // Atomic reading
        System.out.println(synchronizedMethod.getI());
    }
}
