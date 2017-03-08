package zookeeper35;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * Created by root on 17-3-8.
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

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startZK();

        String s = client.queueCommand("this is first task");
        System.out.println(s);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String queueCommand(String s) {
        while (true) {
            String name = null;
            try {
                //队列化的前缀是  task-
                name = zooKeeper.create("/tasks/task-", s.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
                return name;
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
