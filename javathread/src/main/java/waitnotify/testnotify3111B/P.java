package waitnotify.testnotify3111B;

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
            while (!ValueObject.value.equals("")) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String value = System.currentTimeMillis() + "_" + System.nanoTime();

            System.out.println("set 的值是:" + value);

            ValueObject.value = value;

            //随机通知会造成 假死
            lock.notify();
            //建议使用 notifyAll()
            //lock.notifyAll();
        }
    }
}
