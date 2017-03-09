package zookeeper442;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;

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
        System.out.println("这是zookeeper分布式过程协同第四章4.4.2节的代码");
    }

    Watcher workersChangeWatcher = (WatchedEvent e) -> {
        if (e.getType() == Event.EventType.NodeChildrenChanged) {
            assert "/workers".equals(e.getPath());
            getWorks();
        }
    };

    private void getWorks() {
        zk.getChildren("/workers", workersChangeWatcher, workersGetChildrenCallback, null);

    }

    AsyncCallback.ChildrenCallback workersGetChildrenCallback = (int rc, String path, Object ctx, List<String> children) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
//                    getWorkList();
                break;
            case OK:
                reassignAndSet(children);
                break;
            default:
                System.out.println("somthing wrong ");
        }
    };

    //    ChildrenCache workersCache;
    private void reassignAndSet(List<String> children) {
        List<Process> toprocess;

    }

}
