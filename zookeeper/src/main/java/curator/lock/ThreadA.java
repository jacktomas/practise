package curator.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 17-3-17.
 */
public class ThreadA extends Thread {
    @Override
    public void run() {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();
        FakeLimitedResource resource = new FakeLimitedResource();

        ExampleClientThatLocks exampleClientThatLocks = new ExampleClientThatLocks(client, "/cluster-work/lock", resource, Thread.currentThread().getName());
        try {
            exampleClientThatLocks.dowork(8, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
