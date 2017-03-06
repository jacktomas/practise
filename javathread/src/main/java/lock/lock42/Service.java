package lock.lock42;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by root on 17-3-6.
 */
public class Service {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void read() {
        lock.readLock();
        System.out.println("get read lock " + Thread.currentThread().getName() + " " + System.currentTimeMillis());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
