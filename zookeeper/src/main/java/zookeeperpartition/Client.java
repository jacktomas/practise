package zookeeperpartition;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 17-3-14.
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

    void getPartition() {
        zk.getChildren("/partitions", this, cb, null);
    }

    AsyncCallback.ChildrenCallback cb =
            (int rc, String path, Object ctx, List<String> children) -> {
                switch (KeeperException.Code.get(rc)) {
                    case CONNECTIONLOSS:
                        getPartition();
                        break;
                    case OK:
                        if (children.size() > 0) {
                            choosePartition(children);
                        }
                        break;
                    default:
                        System.out.println(" something wrong ");

                }

            };

    private void choosePartition(List<String> children) {
        System.out.println(children);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getType() == Event.EventType.NodeChildrenChanged) {
            getPartition();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();

        client.startZK();

        client.getPartition();

        while (true) {

        }

    }

}
