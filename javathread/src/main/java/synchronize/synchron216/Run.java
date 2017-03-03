package synchronize.synchron216;

/**
 * Created by root on 17-3-3.
 */
public class Run {
    public static void main(String[] args) {
        Service service = new Service();
        MyThread myThread = new MyThread(service);
        myThread.start();
    }
}
