package lock.lock414;

/**
 * Created by root on 17-3-6.
 */
public class Run {
    public static void main(String[] args) {
        try {
            MyService myService = new MyService();
            ThreadA threadA = new ThreadA(myService);
            threadA.start();
            Thread.sleep(1000);
            myService.sinal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
