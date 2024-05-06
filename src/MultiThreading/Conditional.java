package MultiThreading;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    Provider-Consumer Synchronize Problem with Condition's await() and signalAll()
*/
public class Conditional<T> {

    private final Lock lock;
    private final Condition condition;
    private final Queue<T> data;

    public Conditional() {
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.data = new PriorityQueue<>();
    }

    public void provide(T element) {
        try {
            lock.lock();
            data.offer(element);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T consume() throws InterruptedException {
        try {
            lock.lock();
            while (data.isEmpty())
                condition.await();
            return data.remove();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Conditional<Integer> conditional = new Conditional<>();

        // The first thread which call the provide method of MultiThreading.AtomicExample class and send number to queue.
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                conditional.provide(i);
                System.out.println("Provided: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignore) { }
            }
        }).start();

        // The second thread which call the consume method of MultiThreading.AtomicExample class for remove number from queue.
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    System.err.println("Consumed: " + conditional.consume());
                    Thread.sleep(100);
                } catch (InterruptedException ignore) { }
            }
        }).start();
    }
}
