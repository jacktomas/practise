package synchronize.synchron212;

/**
 * Created by root on 17-3-3.
 */
public class ThreadB extends Thread {
    private HasSelfPrivateNum numPre;

    public ThreadB(HasSelfPrivateNum numPre) {
        this.numPre = numPre;
    }

    @Override
    public void run() {
        numPre.addI("b");
    }
}
