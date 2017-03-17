package atomic;

/**
 * Created by root on 17-3-17.
 */
public class Run {
    public static void main(String[] args) {
        AtomicBooleanTest atomicBooleanTest = new AtomicBooleanTest();
        for (int i = 0; i < 3; i++) {

            new ThreadA(atomicBooleanTest).start();

        }

    }
}
