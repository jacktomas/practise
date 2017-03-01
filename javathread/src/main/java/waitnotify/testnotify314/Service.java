package waitnotify.testnotify314;

/**
 * Created by root on 17-3-1.
 */
public class Service {
    public void testMethod(Object lock) {
        synchronized (lock) {
            System.out.println("begin wait() ThreadName=" + Thread.currentThread().getName());
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("    end wait() ThreadName=" + Thread.currentThread().getName());

        }
    }

    public void synNotifyMethod(Object lock) {

        synchronized (lock) {
            System.out.println("begin notify() ThreadName=" + Thread.currentThread().getName() + " time"
                    + System.currentTimeMillis());

            lock.notify();

            try {
                Thread.sleep(5000);

                System.out.println("    end notify() ThreadName=" + Thread.currentThread().getName() + " time="
                        + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
