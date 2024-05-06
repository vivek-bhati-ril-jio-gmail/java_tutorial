public class Thread {

    static class ThreadA extends java.lang.Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(java.lang.Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) {
        ThreadA thread = new ThreadA();
        thread.start(); // starting thread
    }
}
