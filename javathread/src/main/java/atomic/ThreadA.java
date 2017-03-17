package atomic;

/**
 * Created by root on 17-3-17.
 */
public class ThreadA extends Thread {
    private AtomicBooleanTest atomicBooleanTest;

    public ThreadA(AtomicBooleanTest atomicBooleanTest) {
        this.atomicBooleanTest = atomicBooleanTest;
    }

    @Override
    public void run() {
        atomicBooleanTest.getResource();
    }
}
