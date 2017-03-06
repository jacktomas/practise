package lock.lock417;

/**
 * Created by root on 17-3-6.
 */
public class Run {
    public static void main(String[] args) {
        MyService myService = new MyService();
        ThreadA threadA = new ThreadA(myService);
        threadA.start();
        ThreadB threadB = new ThreadB(myService);
        threadB.start();
    }
}
