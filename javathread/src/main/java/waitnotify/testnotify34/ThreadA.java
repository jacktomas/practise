package waitnotify.testnotify34;

/**
 * Created by root on 17-3-2.
 */
public class ThreadA extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("在ThreadA线程中取值=" + Tools.t1.get());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
