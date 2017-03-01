package waitnotify.testnotify314;

/**
 * Created by root on 17-3-1.
 */
public class Test {
    public static void main(String[] args) {
        Object lock = new Object();
        ThreadA threadA = new ThreadA(lock);
        threadA.start();

        NotifyThread notifyThread = new NotifyThread(lock);
        notifyThread.start();

        synNotifyMethodThread synNotifyMethodThread = new synNotifyMethodThread(lock);
        synNotifyMethodThread.start();

    }
}
