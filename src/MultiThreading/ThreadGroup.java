package MultiThreading;

public class ThreadGroup {

    public static void main(String[] args) {
        java.lang.ThreadGroup groupA = new java.lang.ThreadGroup("Group MultiThreading.AtomicExample");
        java.lang.ThreadGroup groupB = new java.lang.ThreadGroup("Group B");

        java.lang.Thread a = new java.lang.Thread(groupA, () -> System.out.println("A1"));
        java.lang.Thread b = new java.lang.Thread(groupA, () -> System.out.println("A2"));
        java.lang.Thread c = new java.lang.Thread(groupB, () -> System.out.println("B1"));

        // Get active threads count in thread's group.
        System.out.println(a.getThreadGroup().activeCount());

        // Get thread group name.
        System.out.println(c.getThreadGroup().getName());
    }
}
