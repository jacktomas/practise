package lock.lock417;

/**
 * Created by root on 17-3-6.
 */
public class ThreadA extends Thread {
    private MyService myService;

    public ThreadA(MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            myService.get();
        }
    }
}
