package zookeeper321;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * Created by root on 17-3-7.
 */
public class Master implements Watcher {

    ZooKeeper zooKeeper;
    String hostport;

    public Master(String hostport) {
        this.hostport = hostport;
    }

    void startZK() throws IOException {
        zooKeeper = new ZooKeeper(hostport, 2000, this);
    }

    void stopZK() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public static void main(String[] args) throws IOException {
        Master master = new Master("127.0.0.1:2181");
        master.startZK();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        master.stopZK();
    }
}
