package executor;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 17-3-11.
 */
public class Compute implements Runnable {
    private int a;
    private int b;

    public Compute(int a, int b) {
        this.a = a;
        this.b = b;
    }

    void compute() {
        System.out.println(a + b);
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(a + b);
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        Compute compute1 = new Compute(1, 2);
        Compute compute2 = new Compute(2, 2);
        pool.submit(compute1);
        pool.submit(compute2);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("wait to print");

    }
}
