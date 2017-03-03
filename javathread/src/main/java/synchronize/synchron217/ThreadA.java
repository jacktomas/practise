package synchronize.synchron217;

/**
 * Created by root on 17-3-3.
 */
public class ThreadA extends Thread {
    private Service service;

    public ThreadA(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.testMethod();

    }
}
