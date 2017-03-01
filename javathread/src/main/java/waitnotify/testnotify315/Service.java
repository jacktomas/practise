package waitnotify.testnotify315;

/**
 * Created by root on 17-3-1.
 */
public class Service {
    public void testMethod(Object lock) {
        synchronized (lock) {
            System.out.println("begin wait()");
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("出现异常啦，因为呈wait状态的线程被interrupt");

            }

        }
    }
}
