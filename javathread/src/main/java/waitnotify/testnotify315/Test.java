package waitnotify.testnotify315;

/**
 * Created by root on 17-3-1.
 */
public class Test {
    public static void main(String[] args) {
        Object lock = new Object();
        ThreadA threadA = new ThreadA(lock);
        threadA.start();
        try {
            Thread.sleep(5000);
            threadA.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
