package waitnotify.testnotify313B;

/**
 * Created by root on 17-3-1.
 */
public class Run {
    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        ThreadA threadA = new ThreadA(lock);
        threadA.start();
        Thread.sleep(50);
        ThreadB threadB = new ThreadB(lock);
        threadB.start();
    }
}
