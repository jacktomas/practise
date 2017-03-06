package lock.lock414;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by root on 17-3-6.
 */
public class MyService {
    private Lock lock = new ReentrantLock();
    public Condition condition = lock.newCondition();

    public void await() {
        try {
            lock.lock();
            System.out.println("await 时间为 " + System.currentTimeMillis());
            condition.await();
            System.out.println("await 之后的时间 " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void sinal() {
        lock.lock();
        System.out.println("signal 时间为 " + System.currentTimeMillis());
        condition.signal();
        lock.unlock();
    }
}
