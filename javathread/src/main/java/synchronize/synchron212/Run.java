package synchronize.synchron212;

/**
 * Created by root on 17-3-3.
 */
public class Run {
    public static void main(String[] args) {
        HasSelfPrivateNum hasSelfPrivateNum = new HasSelfPrivateNum();

        ThreadA threadA = new ThreadA(hasSelfPrivateNum);
        ThreadB threadB = new ThreadB(hasSelfPrivateNum);

        threadA.start();
        threadB.start();
    }
}
