package synchronize.synchronize225;

/**
 * Created by root on 17-3-3.
 */
public class ObjectService {
    public void servicemethodA() {
        synchronized (this) {
            try {
                System.out.println("A begin time =" + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("A end =" + System.currentTimeMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void serviceMethodB() {
        synchronized (this) {

            System.out.println("B begin time =" + System.currentTimeMillis());

            System.out.println("B end time=" + System.currentTimeMillis());

        }
    }
}
