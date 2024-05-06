import java.util.PriorityQueue;
import java.util.Queue;

/*
    Provider-Consumer Synchronize Problem with wait() and notifyAll()
*/
public class WaitNotify<E> {

    private final Queue<E> data;

    public WaitNotify() {
        data = new PriorityQueue<>();
    }

    public synchronized void provide(E element) throws InterruptedException {
        if (data.offer(element)) {
            System.out.println("Provided: " + data.size());
            this.notifyAll();
        } else {
            System.err.println("An element is not provided.");
        }
        java.lang.Thread.sleep(300);
    }

    public synchronized void consume() throws InterruptedException {
        while (data.size() <= 0)
            wait();

        java.lang.Thread.sleep(100);
        data.remove();
        System.err.println("Consumed: " + data.size());
        this.notifyAll();
    }

    public static void main(String[] args) {
        WaitNotify<Integer> waitNotify = new WaitNotify<>();

        // The first thread which call the provide method of Atomic class and send number to queue.
        new java.lang.Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    waitNotify.provide(i);
                } catch (InterruptedException ignore) { }
            }
        }).start();

        // The second thread which call the consume method of Atomic class for remove number from queue.
        new java.lang.Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    waitNotify.consume();
                } catch (InterruptedException ignore) { }
            }
        }).start();
    }
}

/*
    Output:

    Provided: 1
    Provided: 1
    Consumed: 0
    Provided: 2
    Consumed: 1
    Consumed: 0
    Provided: 1
    Consumed: 0
    Provided: 1
    Consumed: 0
*/
