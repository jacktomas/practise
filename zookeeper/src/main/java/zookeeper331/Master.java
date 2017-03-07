package zookeeper331;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Random;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * Created by hdfs on 3/7/17.
 */
public class Master {
    static Boolean isLeader;
    Random random = new Random();
    String serverId = Integer.toString(random.nextInt());
    ZooKeeper zk;

    void startZK() {
        try {
            zk = new ZooKeeper("127.0.0.1:2181", 2000, (Watcher) this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static AsyncCallback.StringCallback masterCreateCallback = (int rc, String path, Object ctx, String name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                //checkMaster();
                return;
            case OK:
                isLeader = true;
                break;
            default:
                isLeader = false;
        }
        System.out.println("i'm " + (isLeader ? "" : "not") + " the leader");
    };

    void runForMaster() {
        zk.create("/master", serverId.getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, masterCreateCallback, null);

    }
}
