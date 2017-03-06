package lock.lock411;

/**
 * Created by root on 17-3-6.
 */
public class Run {
    public static void main(String[] args) {
        MyService myService = new MyService();
        MyThread myThread;
        for (int i = 0; i < 5; i++) {
            myThread = new MyThread(myService);
            myThread.start();
        }
    }
}
