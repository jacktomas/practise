package waitnotify.testnotify314;

/**
 * Created by root on 17-3-1.
 */
public class synNotifyMethodThread extends Thread {
    private Object lock;

    public synNotifyMethodThread(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        Service service = new Service();
        service.synNotifyMethod(lock);
    }
}
