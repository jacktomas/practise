package semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by Administrator on 2017/3/16.
 */
public class SemaphoreTest  {
    Semaphore full=new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        SemaphoreTest semaphoreTest = new SemaphoreTest();
       /* try {
            semaphoreTest.full.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("-------");
        semaphoreTest.full.release();
        semaphoreTest.full.release();
        System.out.println("******");
        semaphoreTest.full.acquire();
        System.out.println("11111");
        semaphoreTest.full.acquire();
        System.out.println("22222");
        semaphoreTest.full.acquire();
        System.out.println("3333333333");


    }
}
