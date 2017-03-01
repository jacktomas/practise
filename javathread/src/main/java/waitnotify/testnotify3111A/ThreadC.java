package waitnotify.testnotify3111A;

/**
 * Created by root on 17-3-1.
 */
public class ThreadC extends Thread {
    private C c;

    @Override
    public void run() {
        while (true) {
            c.getValue();
        }
    }

    public ThreadC(C c) {
        this.c = c;
    }
}
