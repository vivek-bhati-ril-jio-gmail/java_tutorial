package MultiThreading;

public class Volatile<T> {

    // 1. The volatile modifier allows not to cache a variable in threads.
    // 2. Provides atomic reading in threads.
    private volatile T data;

    public T getData() {
        return this.data;
    }


    @SuppressWarnings("NonAtomicOperationOnVolatileField")
    public static void main(String[] args) {
        Volatile<Integer> aVolatile = new Volatile<>();

        // MultiThreading.AtomicExample reading
        System.out.println(aVolatile.getData());

        // Non-atomic modification.
        // You need to increment/decrement variable use Atomics from Concurrency.
        aVolatile.data++;
    }
}
