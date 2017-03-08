package zookeeper34;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

/**
 * Created by root on 17-3-8.
 */
public class Master implements Watcher {
    String status;
    ZooKeeper zooKeeper;
    String serverId = Integer.toHexString(new Random().nextInt());

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    void startZK() {
        try {
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void register() {
        zooKeeper.create("/workers/worker-" + serverId, "Idle".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, createWokerCallback, null);
    }

    AsyncCallback.StringCallback createWokerCallback = (int rc, String path, Object ctx, String name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                register();
                break;
            case OK:
                break;
            case NODEEXISTS:
                break;
            default:
                System.out.println("something went wrong");
        }
    };

    public static void main(String[] args) {
        Master master = new Master();
        master.startZK();

        master.register();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setStatus(String status) {
        this.status = status;
        updateStatus(status);

    }

    AsyncCallback.StatCallback statusUpdateCallback = (int rc, String path, Object ctx, Stat stat) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                updateStatus((String) ctx);
                return;
        }
    };

    synchronized private void updateStatus(String status) {
        if (status == this.status) {
            zooKeeper.setData("/workers/worker-" + serverId, status.getBytes(), -1, statusUpdateCallback, status);
        }
    }

}
