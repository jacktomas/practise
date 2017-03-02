package waitnotify.testnotify322;

/**
 * Created by root on 17-3-2.
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        int secondValue = (int) (Math.random() * 1000);
        System.out.println("secondValue: " + secondValue);
        try {
            Thread.sleep(secondValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
