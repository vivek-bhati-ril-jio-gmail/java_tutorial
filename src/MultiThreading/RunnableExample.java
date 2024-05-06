package MultiThreading;

public class RunnableExample {
    static class ThreadA implements java.lang.Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(java.lang.Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        java.lang.Thread thread = new java.lang.Thread(new ThreadA());
        thread.start(); // starting thread
    }
}
