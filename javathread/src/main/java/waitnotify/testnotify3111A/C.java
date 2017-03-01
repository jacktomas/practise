package waitnotify.testnotify3111A;

/**
 * Created by root on 17-3-1.
 */
public class C {
    private String lock;

    public C(String lock) {
        this.lock = lock;
    }

    public void getValue() {
        synchronized (lock) {
            if (ValueObject.value.equals("")) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("get 的值是: " + lock);

            ValueObject.value = "";
            lock.notify();
        }

    }
}
