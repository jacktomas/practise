package waitnotify.testnotify319A;

/**
 * Created by root on 17-3-1.
 */
public class MyRun {
    private String lock = new String("");
    private Runnable runnable = new Runnable() {
        public void run() {
            synchronized (lock) {
                System.out.println("begin wait");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end wait");

            }
        }
    };

    private Runnable runnablb = new Runnable() {
        public void run() {
            synchronized (lock) {
                System.out.println("begin notify ");
                lock.notify();
                System.out.println("end notify");

            }
        }
    };

    public static void main(String[] args) {
        MyRun run = new MyRun();
        Thread a = new Thread(run.runnablb);
        a.start();
        Thread b = new Thread(run.runnable);
        b.start();
    }
}
