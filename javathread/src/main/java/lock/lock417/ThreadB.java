package lock.lock417;

/**
 * Created by root on 17-3-6.
 */
public class ThreadB extends Thread {
    private MyService myService;

    public ThreadB(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run() {
        for (int i = 1; i < 1000; i++) {
            myService.set();
        }
    }
}
