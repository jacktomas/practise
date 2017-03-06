package lock.lock416;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by root on 17-3-6.
 */
public class MyService {
    private Lock lock = new ReentrantLock();
    public Condition conditionA = lock.newCondition();
    public Condition conditionB = lock.newCondition();

    public void awaitA() {
        try {
            lock.lock();
            System.out.println("Thread name is " + Thread.currentThread().getName() + " begin awaitA tims is " + System.currentTimeMillis());
            conditionA.await();
            System.out.println("Thread name is " + Thread.currentThread().getName() + " end awaitA tims is " + System.currentTimeMillis());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void awaitB() {
        try {
            lock.lock();
            System.out.println("Thread name is " + Thread.currentThread().getName() + " begin awaitB tims is " + System.currentTimeMillis());
            conditionB.await();
            System.out.println("Thread name is " + Thread.currentThread().getName() + " end awaitB tims is " + System.currentTimeMillis());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalA() {
        lock.lock();
        System.out.println("signalAll_A time is " + System.currentTimeMillis());
        conditionA.signalAll();
        lock.unlock();
    }

    public void signalB() {
        lock.lock();
        System.out.println("signalAll_B time is " + System.currentTimeMillis());
        conditionA.signalAll();
        lock.unlock();
    }
}
