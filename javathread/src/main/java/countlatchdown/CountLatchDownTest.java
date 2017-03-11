package countlatchdown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 17-3-11.
 */
public class CountLatchDownTest {
    private int a;
    private int b;
    private CountDownLatch countDownLatch;

    public CountLatchDownTest(int a, int b, CountDownLatch countDownLatch) {
        this.a = a;
        this.b = b;
        this.countDownLatch = countDownLatch;
    }

    Runnable addition = () -> {
        try {
            System.out.println("current name:" + Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a + b);
        countDownLatch.countDown();
    };

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        CountLatchDownTest countLatchDownTest1 = new CountLatchDownTest(1, 2, countDownLatch);
        CountLatchDownTest countLatchDownTest2 = new CountLatchDownTest(3, 2, countDownLatch);
//        new Thread(countLatchDownTest1.addition).start();
//        new Thread(countLatchDownTest2.addition).start();
//        countLatchDownTest1.addition.run();
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(countLatchDownTest1.addition);
        countLatchDownTest2.addition.run();
        pool.submit(countLatchDownTest2.addition);


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("等待所有线程执行完");
        }

    }
}
