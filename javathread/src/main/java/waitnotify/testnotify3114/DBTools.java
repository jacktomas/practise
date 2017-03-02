package waitnotify.testnotify3114;

/**
 * Created by root on 17-3-2.
 */
public class DBTools {
    volatile private boolean prevIsA = false;

    synchronized public void backupA() {
        try {
            while (prevIsA) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 5; i++) {
            System.out.println("= = = = =");
        }
        prevIsA = true;
        notifyAll();
    }

    synchronized public void backupB() {
        try {
            while (!prevIsA) {
                wait();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println("- - - - -");
            }
            prevIsA = false;
            notifyAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
