package atomic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by root on 17-3-17.
 */
public class AtomicBooleanTest {
    private AtomicBoolean inuse = new AtomicBoolean(false);

    void getResource() {
        //这是获取AtomicBoolean 修改后 再有其他线程访问时操作才会进入
        if (!inuse.compareAndSet(false, true)) {
            throw new IllegalStateException("current Thread : " + Thread.currentThread().getName());
        }

        /*if (inuse.compareAndSet(false, true)) {
            System.out.println("current thread name is : "+Thread.currentThread().getName());
        }*/

        try {
            System.out.println("current Thread " + Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            inuse.set(false);
        }

/*
        //这是修改了atomicboolean  为true时才进入if
        if (inuse.compareAndSet(false,true)) {
            System.out.println("i am only one");
            System.out.println("currentThread name is : "+Thread.currentThread().getName());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            inuse.set(false);
        }
        else
            System.out.println("wait again");
*/


    }

}
