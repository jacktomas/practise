package synchronize.synchron216;

/**
 * Created by root on 17-3-3.
 */
public class MyThread extends Thread {
    private Service service;

    public MyThread(Service service) {
        this.service = service;
    }

    @Override
    public void run() {

        service.service1();
    }
}
