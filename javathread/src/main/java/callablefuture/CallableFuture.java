package callablefuture;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by root on 17-3-7.
 */
public class CallableFuture {
    int number;

    public CallableFuture(int number) {
        this.number = number;
    }


    Callable<Integer> callable = () -> {
        if (number <= 1) {
            return 1;
        } else {
            int result = 1;
            for (int i = 2; i < 3; i++) {
                result *= i;
            }
            Thread.sleep(4000);
            return result;
        }
    };

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ArrayList<Future<Integer>> result = new ArrayList<>();
        Random random = new Random();
        for (int i = 1; i < 3; i++) {
            int a = random.nextInt(5);
            System.out.println("input data is " + a);
            CallableFuture callableFuture = new CallableFuture(a);
            Future<Integer> future = executorService.submit(callableFuture.callable);
            result.add(future);
        }

        executorService.submit(() -> {
            result.stream().forEach(
                    future -> {
                        try {
                            Integer data = future.get();
                            System.out.println(data + " time is " + System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
            );
        });

        System.out.println("print main :time is " + System.currentTimeMillis());
        executorService.shutdown();
    }

}
