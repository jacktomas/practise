package zookeeper444;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by root on 17-3-9.
 */
public class Client implements Watcher {
    ZooKeeper zk;

    void startZK() {
        try {
            zk = new ZooKeeper("127.0.0.1:2181", 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("this is zookeeper 4.4.4");
    }

    void register(String path) {
        zk.create(path + 111, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, createWorkerCallback, null);
    }

    AsyncCallback.StringCallback createWorkerCallback = (int rc, String path, Object ctx, String name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                register(path);
                break;
            case OK:
                System.out.println("create successfully ");
                break;
            case NODEEXISTS:
                System.out.println("nodeExists");
            default:
                System.out.println("something wrong");
        }
    };

    public static void main(String[] args) {
        Client client = new Client();
        client.startZK();
        client.register("/assign/worker-");
        client.register("/workers/worker-");
        while (true) {

        }
    }
}
