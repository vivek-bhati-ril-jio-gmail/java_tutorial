import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// Callable Example
public class ConcurrencyExamples implements Callable<Integer> {
    public int scaler(int num, int factor) {
        return num*factor;
    }

    public static void main(String[] args) {
        ConcurrencyExamples s = new ConcurrencyExamples();
        int result = s.scaler(10, 5);
        System.out.println(result);
    }

    @Override
    public Integer call() {
        return scaler(10, 5);
    }
}

// Runnable Example
class CallableAndFutureAndRunnableExample implements Runnable {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {

            // Submit a task and obtain a Future object
            Future<Integer> future = executorService.submit(() -> {
                Thread.sleep(2000);
                return 42;
            });

            // Perform other tasks while the computation is in progress

            // Wait for the result and retrieve it
            Integer result = future.get();
            System.out.println(result);

            // Shutdown the executor service
            executorService.shutdown();
        }
    }

    @Override
    public void run() {
        // Do whatever task you want to do.
        // Notice, that you do not have to return anything.
    }
}

class SynchronizedBlockExample {
    private int count = 0;
    // Atomic lock object for synchronization
    private final Object lock = new Object();

    public void performTask() {
        // Synchronized block using the 'lock' object
        synchronized (lock) {
            for (int i = 0; i < 1000; i++) {
                this.count++;
            }
        }
    }
}

class SynchronizedExample {
    private static int count = 0;

    // Synchronized instance method
    public synchronized void increment() {
        count++;
    }

    // Synchronized static method
    public static synchronized void decrement() {
        count--;
    }
}

class Reentrantcy {
    private int count = 0;

    public synchronized void doAll() {
        increment();
        decrement();
    }

    public synchronized void increment() {
        count++;
    }

    public synchronized void decrement() {
        count--;
    }
}

class WaitNotifyExample {
    private static final Object lock = new Object();
    private static boolean isOddTurn = true;

    public static void main(String[] args) {
        Thread oddThread = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                synchronized (lock) {
                    while (!isOddTurn) {
                        try {
                            // Wait until it's the odd thread's turn
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Odd: " + i);
                    // Satisfy the waiting condition
                    isOddTurn = false;
                    // Notify the even thread
                    lock.notify();
                }
            }
        });

        Thread evenThread = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                synchronized (lock) {
                    while (isOddTurn) {
                        try {
                            // Wait until it's the even thread's turn
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Even: " + i);
                    // Satisfy the waiting condition
                    isOddTurn = true;
                    // Notify the odd thread
                    lock.notify();
                }
            }
        });

        oddThread.start();
        evenThread.start();
    }
}

class VolatileExample {
    private static volatile boolean flag = false;

    public static void main(String[] args) {
        Thread writerThread = new Thread(() -> {
            try {
                Thread.sleep(1000); // Simulate some work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            flag = true; // Set the flag to true
            System.out.println("Flag set to true by writerThread.");
        });

        Thread readerThread = new Thread(() -> {
            while (!flag) {
                // Busy-wait until the flag becomes true
            }
            System.out.println("Flag is true, readerThread can proceed.");
        });

        writerThread.start();
        readerThread.start();
    }
}

class Singleton {
    private static volatile Singleton _instance; // volatile variable
    public static Singleton getInstance() {
        if (_instance == null) {
            synchronized (Singleton.class) {
                if (_instance == null)
                    _instance = new Singleton();
            }
        }
        return _instance;
    }
}

class ThreadLocalExample {
    private static ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // Create and start three threads
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                // Get the thread-specific value
                System.out.println("Thread 1: before: " + threadLocal.get());
                // Set a thread-specific value
                threadLocal.set(1);
                // Get the thread-specific value
                System.out.println("Thread 1: : after: " + threadLocal.get());
                // Notify the odd thread
                lock.notifyAll();
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                // Get the thread-specific value
                System.out.println("Thread 2: before: " + threadLocal.get());
                // Set a thread-specific value
                threadLocal.set(2);
                // Get the thread-specific value
                System.out.println("Thread 2: : after: " + threadLocal.get());
                // Notify the odd thread
                lock.notifyAll();
            }
        });

        Thread thread3 = new Thread(() -> {
            synchronized (lock) {
                // Get the thread-specific value
                System.out.println("Thread 3: before: " + threadLocal.get());
                // Set a thread-specific value
                threadLocal.set(3);
                // Get the thread-specific value
                System.out.println("Thread 3: : after: " + threadLocal.get());
                // Notify the odd thread
                lock.notifyAll();
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

class DeadlockExample {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Holding lock 1...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread 1: Waiting for lock 2...");
                // Should have written notifyAll() here
                synchronized (lock2) {
                    System.out.println("Thread 1: Acquired lock 2.");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Holding lock 2...");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread 2: Waiting for lock 1...");
                synchronized (lock1) {
                    System.out.println("Thread 2: Acquired lock 1.");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}

class LockExample {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        // Type 1: Lock control using try catch
        try {
            System.out.println("Inside Try - 1 Block");
            // Critical section
        } finally {
            System.out.println("Inside Finally - 1 Block");
            lock.unlock();
        }

        // Type 2: Lock on read and write
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock = readWriteLock.readLock();
        readLock.lock();
        try {
            System.out.println("Inside Try - 2 Block");
            // Read operation
        } finally {
            System.out.println("Inside Finally - 2 Block");
            readLock.unlock();
        }
    }
}

/*
* Deprecated in JDK 1.1
*
* Thread.stop() is being phased out due to its inherent risk.
* When you stop a thread, it unlocks all the monitors it has locked.
* Other threads might see these objects in an inconsistent state if any
* of the objects previously protected by these monitors were in an inconsistent state.
*
*
* Thread.suspend() is deprecated because it is inherently deadlock-prone.
* As a result, Thread.resume() must be deprecated as well.
* When the target thread is suspended, it holds a lock on the monitor
* protecting a crucial system resource, and no other thread may access it
* until the target thread is resumed.
* Deadlock occurs if the thread that would restart the target thread
* tries to lock this monitor before invoking resume().
* */

/*
* Ways to avoid deadlock:
*
* Use a Timeout: Set a timeout for acquiring locks.
* If a thread cannot acquire a lock within a specified time, it can release any locks it holds and retry or abort.
* This functionality can be easily implemented using ReentrantLock from the java.util.concurrent.locks package.
* Lock Ordering: Establish a consistent order for acquiring locks across all threads to prevent circular waiting as seen in the example below.
* Resource Allocation Graph: Use algorithms like the resource allocation graph to detect and recover from deadlocks.
* Design for Deadlock Avoidance: Design your multi-threaded code to minimize the potential for deadlocks,
* such as using higher-level abstractions like the java.util.concurrent classes.
* */
class DeadlockResolutionExample {
    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Runnable acquireLocks = () -> {
            lock1.lock();
            try {
                System.out.println(Thread.currentThread().getName() + ": Holding lock 1...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + ": Waiting for lock 2...");
                // Attempt to acquire lock2
                boolean acquiredLock2 = false;
                try {
                    acquiredLock2 = lock2.tryLock(0, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (acquiredLock2) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ": Acquired lock 2.");
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    System.out.println(Thread.currentThread().getName() + ": Timeout while waiting for lock 2.");
                }
            } finally {
                lock1.unlock();
            }
        };

        // Consistent order for acquiring locks and use of timeouts
        Thread thread1 = new Thread(acquireLocks);
        Thread thread2 = new Thread(acquireLocks);

        thread1.start();
        thread2.start();
    }
}

/*
* Some useful terms
*
* Livelock: is a situation where two or more threads are actively trying to resolve a conflict
* but end up causing repeated state changes without making any progress.
* In a livelock, threads are not blocked but are busy responding to each other’s actions, and the system remains in an undesirable state.
*
* Thread starvation: occurs when a thread is unable to make progress because it is constantly
* waiting for a resource or access to a critical section that is always being acquired by other threads.
* This results in the affected thread not getting a fair share of CPU time.
*
* Executor: is an interface that represents an object capable of executing tasks asynchronously.
* It decouples the task submission from task execution.
*
* ExecutorService: is a sub-interface of Executor that extends the functionality by providing methods
* for managing the lifecycle of the executor and controlling the execution of tasks.
* In other words, ExecutorService is the core interface for thread pools.
* */


class ExecutorServiceExample {

    public static void main(String[] args) {
        // Create an ExecutorService using a fixed-size thread pool with 2 threads.
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {

            // Define a Runnable task
            Runnable runnableTask = () -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Task 1 executed by " + threadName);
            };

            // Define a list of Callable tasks
            List<Callable<String>> callableTasks = List.of(
                    () -> {
                        String threadName = Thread.currentThread().getName();
                        return "Task 2 executed by " + threadName;
                    },
                    () -> {
                        String threadName = Thread.currentThread().getName();
                        return "Task 3 executed by " + threadName;
                    }
            );

            // Submit the task to the ExecutorService
            executorService.submit(runnableTask);

            try {
                // Use invokeAll to submit a list of Callable tasks and wait for all tasks to complete.
                List<Future<String>> futures = executorService.invokeAll(callableTasks);

                // Print the results of the Callable tasks, call to get() waits until the result is available
                for (Future<String> future : futures) {
                    System.out.println(future.get());
                }

                // Use invokeAny to submit a list of Callable tasks and wait for the first completed task.
                String firstResult = executorService.invokeAny(callableTasks);
                System.out.println("First completed task: " + firstResult);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Shutdown the ExecutorService to stop accepting new tasks
            executorService.shutdown();
        }
    }
}