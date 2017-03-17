package curator.lock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by root on 17-3-17.
 */
public class FakeLimitedResource {
    private final AtomicBoolean inuse = new AtomicBoolean(false);

    public void use() {
        if (!inuse.compareAndSet(false, true)) {
            throw new IllegalStateException("Need to be used by one client at a time");
        }
        try {
            //Thread.sleep((long) (Math.random() * 10));
            Thread.sleep(2000);
            System.out.println("current thread name is : " + Thread.currentThread().getName() + " this is limited resource " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            inuse.set(false);
        }
    }
}
