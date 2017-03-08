package zookeeper332;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by root on 17-3-8.
 */

/**
 * 设置元数据
 */
public class Master implements Watcher {

    ZooKeeper zk;

    void startZk() {
        try {
            zk = new ZooKeeper("127.0.0.1:2181", 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    //启动方法
    public void bootstrap() {
        CreateParent("/workers", new byte[0]);
        CreateParent("/assign", new byte[0]);
        CreateParent("/tasks", new byte[0]);
        CreateParent("/status", new byte[0]);
    }

    AsyncCallback.StringCallback createParentCallback = (int rc, String path, Object ctx, String name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                CreateParent(path, (byte[]) ctx);
                break;
            case OK:
                break;
            case NODEEXISTS:
                break;
            default:
                System.out.println("something went wrong");
        }
    };

    //创建函数
    void CreateParent(String path, byte[] data) {
        zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, createParentCallback, data);
    }

    public static void main(String[] args) {
        Master master = new Master();
        master.startZk();

        master.bootstrap();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
