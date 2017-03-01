package waitnotify.testnotify313A;

/**
 * Created by root on 17-3-1.
 */
public class MyThread1 extends Thread {
    private Object lock;

    public MyThread1(Object lock) {
        super();
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            System.out.println("begin  waite time =" + System.currentTimeMillis());
            try {
                lock.wait();
                System.out.println("end   waite time=" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
