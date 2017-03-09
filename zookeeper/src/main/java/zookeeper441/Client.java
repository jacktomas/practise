package zookeeper441;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

/**
 * Created by root on 17-3-9.
 * 大概思路是：创建主节点 /master
 * 1:创建成功则
 * 竞争成功：则行使领导权：takeLeadership()
 * 且创建监视点
 * 2：节点已经存在
 * 创建监视点监视主节点
 * 监视点监视/master 是否被删除
 * 1：被删除，则参与竞争
 * 2：
 */
public class Client implements Watcher {

    ZooKeeper zk;
    Random random = new Random();
    String serverId = Integer.toString(random.nextInt());

    void startZK() {
        try {
            zk = new ZooKeeper("127.0.0.1:2181", 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //检查master是否存在的回调函数
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

    //检查节点是否存在的方法
    void checkMaster() {
        zk.getData("/master", false, masterCheckCallback, null);
    }

    //创建主节点的代码
    void runForMaster() {
        zk.create("/master", serverId.getBytes(), OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, masterCreateCallback, null);

    }

    //传建主节点的回调函数
    AsyncCallback.StringCallback masterCreateCallback = (int rc, String path, Object ctx, String name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                checkMaster();
                break;
            case OK:
//                String state = MasterStates.ELECTED;
//                takeLeadership();
                System.out.println("create znode run time " + System.currentTimeMillis());
                masterExists();
                break;
            case NODEEXISTS:
//                String state = MasterStates.NOTELECTED;
                masterExists();
                break;
            default:
//                String state = MasterStates.NOTELECTED;
                System.out.println("something wrong ");

        }

    };

    //设置主节点监听点函数
    void masterExists() {
        zk.exists("/master", masterExistWatcher, masterExistsCallback, null);
    }

    //监听点接口函数的实现
    Watcher masterExistWatcher = (WatchedEvent e) -> {
        System.out.println("watcher time " + System.currentTimeMillis());
        if (e.getType() == Watcher.Event.EventType.NodeDeleted) {
            assert "/master".equals(e.getPath());
            System.out.println("watcher read to create znode run time : " + System.currentTimeMillis());
            runForMaster();
        }
    };
    //监听点回调函数
    AsyncCallback.StatCallback masterExistsCallback = (int rc, String path, Object ctx, Stat stat) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                masterExists();
                break;
            case OK:
                System.out.println("call back time : " + System.currentTimeMillis());
                if (stat == null) {
                    System.out.println("stat is null,time is :" + System.currentTimeMillis());
//                    String state = MasterStates.RUNNing;
                    runForMaster();
                }
                break;
            default:
                checkMaster();
                break;
        }
    };

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("this is main ");
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startZK();
        client.runForMaster();


        synchronized (client) {
            try {
                client.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
