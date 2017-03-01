package waitnotify.testnotify3111B;

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
            while (ValueObject.value.equals("")) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("get 的值是: " + lock);

            ValueObject.value = "";
            //随机通知会造成 假死
            lock.notify();
            //建议使用 notifyAll()
            //lock.notifyAll();
        }

    }
}
