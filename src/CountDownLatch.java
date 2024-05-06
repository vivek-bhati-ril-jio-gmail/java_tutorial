public class CountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(3);

        new Thread(new MyTread(latch)).start();
        sleep();

        new Thread(new MyTread(latch)).start();
        sleep();

        new Thread(new MyTread(latch)).start();
        sleep();

        latch.await();
        System.out.println("AFTER AWAIT...");
    }

    private static void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class MyTread implements Runnable {

        private final java.util.concurrent.CountDownLatch latch;

        MyTread(java.util.concurrent.CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            latch.countDown();
            System.out.println("AFTER COUNT DOWN...");
        }
    }
}
