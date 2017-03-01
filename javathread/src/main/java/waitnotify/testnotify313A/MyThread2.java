package waitnotify.testnotify313A;

/**
 * Created by root on 17-3-1.
 */
public class MyThread2 extends Thread {
    private Object lock;

    public MyThread2(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            System.out.println("开始 notifiy time " + System.currentTimeMillis());
            lock.notify();
            System.out.println("结束 notify time  " + System.currentTimeMillis());
        }
    }
}
