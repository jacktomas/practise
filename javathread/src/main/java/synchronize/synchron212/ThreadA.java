package synchronize.synchron212;

/**
 * Created by root on 17-3-3.
 */
public class ThreadA extends Thread {
    private HasSelfPrivateNum numref;

    public ThreadA(HasSelfPrivateNum numref) {
        this.numref = numref;
    }

    @Override
    public void run() {
        numref.addI("a");
    }
}
