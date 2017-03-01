package waitnotify.testnotify313A;

/**
 * Created by root on 17-3-1.
 */
public class Test {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock = new Object();
        MyThread1 t1 = new MyThread1(lock1);
        t1.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MyThread2 t2 = new MyThread2(lock);
        t2.start();


    }
}
