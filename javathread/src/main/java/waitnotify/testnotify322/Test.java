package waitnotify.testnotify322;

/**
 * Created by root on 17-3-2.
 */
public class Test {
    public static void main(String[] args) {
        MyThread threadTest = new MyThread();
        threadTest.start();
        try {
            threadTest.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("我想等子线程执行完毕后再执行，我做到了");

    }
}
