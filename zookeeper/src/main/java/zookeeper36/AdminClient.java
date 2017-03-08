package zookeeper36;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Date;

/**
 * Created by root on 17-3-8.
 */
public class AdminClient implements Watcher {
    ZooKeeper zooKeeper;

    void startZK() {
        try {
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void listState() {
        try {
            Stat stat = new Stat();
            byte[] data = zooKeeper.getData("/master", false, stat);
            Date startDate = new Date(stat.getCtime());
            String state = new String(data);
            System.out.println("master: " + state + " since " + startDate);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("no master");
        }

        System.out.println("println works");
        try {
            for (String w : zooKeeper.getChildren("/workers", false)) {
                byte[] data = zooKeeper.getData("/workers/" + w, false, null);
                String state = new String(data);
                System.out.println("\t " + w + " :" + state);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Tasks:");
        try {
            for (String s : zooKeeper.getChildren("/assign", false)) {
                System.out.println("\t" + s);
            }
        } catch (KeeperException e) {
            System.out.println(" no node");
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }

    public static void main(String[] args) {
        AdminClient adminClient = new AdminClient();
        adminClient.startZK();

        adminClient.listState();
    }
}
