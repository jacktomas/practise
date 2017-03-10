package zookeeper46;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by root on 17-3-10.
 * <p/>
 * 这是zookeeper 分布式协同过滤里面 4.6章节的想法实现：
 * 为客户端缓存数据，并在需要的时候使用这些数据，一旦这些数据发生变化，就会通知客户端也更新本地缓存的数据
 */
public class Client implements Watcher {
    ZooKeeper zooKeeper;

    void startZK() {
        try {
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //1：设置数据监视监视点，监视数据变化
    void getDataChange() {
        zooKeeper.getData("/property", datachangeWatcher, cb, null);
    }

    Watcher datachangeWatcher = (WatchedEvent e) -> {
        //2：数据发生了变化，则继续监听
        if (e.getType() == Event.EventType.NodeDataChanged) {
            getDataChange();
        }
    };
    //3：数据发生了变化,调用回调函数
    AsyncCallback.DataCallback cb = (int rc, String path, Object ctx, byte[] data, Stat stat) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                getDataChange();
                break;
            case OK:
                //4：调用处理函数
                processChangeData(data);
                break;
            default:
                System.out.println(" someting wrong ");
        }
    };

    private void processChangeData(byte[] data) {
        System.out.println(new String(data));
    }


    @Override
    public void process(WatchedEvent event) {
        System.out.println("process begin");
    }

    public static void main(String[] args) {
        //初始化client
        Client client = new Client();
        //初始化 zookeeper
        client.startZK();
        //开始监听
        client.getDataChange();
        while (true) {

        }
    }
}
