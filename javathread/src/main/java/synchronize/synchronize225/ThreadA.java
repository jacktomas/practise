package synchronize.synchronize225;

/**
 * Created by root on 17-3-3.
 */
public class ThreadA extends Thread {
    private ObjectService objectService;

    public ThreadA(ObjectService objectService) {
        this.objectService = objectService;
    }

    @Override
    public void run() {
        objectService.servicemethodA();
    }
}
