package waitnotify.testnotify3111A;

/**
 * Created by root on 17-3-1.
 */
public class P {
    private String lock;

    public P(String lock) {
        this.lock = lock;
    }

    public void setValue() {
        synchronized (lock) {
            if (!ValueObject.value.equals("")) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String value = System.currentTimeMillis() + "_" + System.nanoTime();

            System.out.println("set 的值是:" + value);

            ValueObject.value = value;

            lock.notify();
        }
    }
}
