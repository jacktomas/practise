package curator.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 17-3-17.
 */
public class ExampleClientThatLocks {
    private final InterProcessMutex lock;

    private final FakeLimitedResource fakeLimitedResource;

    private final String clientName;

    public ExampleClientThatLocks(CuratorFramework client, String lockPath, FakeLimitedResource fakeLimitedResource, String clientName) {
        this.lock = new InterProcessMutex(client, lockPath);
        this.fakeLimitedResource = fakeLimitedResource;
        this.clientName = clientName;
    }

    public void dowork(long time, TimeUnit unit) throws Exception {
        if (!lock.acquire(time, unit)) {
            System.out.println("current thread name is : " + Thread.currentThread().getName());
        }
        try {
            System.out.println(clientName + " has the lock,time is :" + System.currentTimeMillis());
            fakeLimitedResource.use();
        } finally {
            System.out.println(clientName + " : release the lock ,time is :" + System.currentTimeMillis());
            lock.release();
        }

    }

}
