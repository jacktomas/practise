package lock.lock411;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by root on 17-3-6.
 */
public class MyService {
    private Lock lock = new ReentrantLock();

    public void testMethod() {

        lock.lock();
        for (int i = 0; i < 5; i++) {
            System.out.println("thread name is :  " + Thread.currentThread().getName() + " i is  : " + i);
        }
        lock.unlock();
    }
}
