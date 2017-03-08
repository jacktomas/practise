package zookeeper331;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * Created by hdfs on 3/7/17.
 */
public class Master implements Watcher {
    static Boolean isLeader;
    Random random = new Random();
    String serverId = Integer.toString(random.nextInt());
    ZooKeeper zk;

    void startZK() {
        try {
            zk = new ZooKeeper("127.0.0.1:2181", 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //create 的异步实现
    AsyncCallback.StringCallback masterCreateCallback = (int rc, String path, Object ctx, String name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                checkMaster();
                return;
            case OK:
                isLeader = true;
                break;
            default:
                isLeader = false;
        }
        System.out.println("i'm " + (isLeader ? "" : "not") + " the leader");
    };

    //检查master是否存在
    AsyncCallback.DataCallback masterCheckCallback = (int rc, String path, Object ctx, byte[] data, Stat stat) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                checkMaster();
                return;
            case NONODE:
                runForMaster();
                return;
        }
    };

    void checkMaster() {
        zk.getData("/master", false, masterCheckCallback, null);
    }

    void runForMaster() {
        zk.create("/master", serverId.getBytes(), OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, masterCreateCallback, null);

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) {
        Master master = new Master();
        master.startZK();

        master.runForMaster();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
