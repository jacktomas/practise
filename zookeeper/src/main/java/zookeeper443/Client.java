package zookeeper443;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 17-3-9.
 */
public class Client implements Watcher {
    ZooKeeper zooKeeper;

    void startzk() {
        try {
            zooKeeper = new ZooKeeper("127.0.0.1:2181", 2000, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getTasks() {
        zooKeeper.getChildren("/tasks", taskChangeWatcher, taskGetChildrenCallback, null);
    }

    Watcher taskChangeWatcher = (WatchedEvent e) -> {
        System.out.println(" get task change " + e.getPath());
        if (e.getType() == Event.EventType.NodeChildrenChanged) {
            assert "/tasks".equals(e.getPath());
            getTasks();
        }
    };
    AsyncCallback.ChildrenCallback taskGetChildrenCallback = (int rc, String path, Object ctx, List<String> children) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                getTasks();
                break;
            case OK:
                if (children != null) {
                    System.out.println(" get children :" + children);
                    assginTasks(children);
                }
                break;
            default:
                System.out.println("this is other error");
        }

    };

    private void assginTasks(List<String> tasks) {
        for (String task : tasks) {
            getTaskData(task);
        }

    }

    private void getTaskData(String task) {
        System.out.println("getTaskData :" + task);
        zooKeeper.getData("/tasks/" + task, false, taskDataCallback, task);
    }

    AsyncCallback.DataCallback taskDataCallback = (int rc, String path, Object ctx, byte[] data, Stat stat) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                getTaskData((String) ctx);
                break;
            case OK:
                try {
                    List<String> children = zooKeeper.getChildren("/tasks", this);
                    Random random = new Random();
                    int i = random.nextInt(children.size());
                    String designateworker = children.get(i);
                    System.out.println("designateworker:  " + designateworker);
                    String assignmentPath = "/assign/" + designateworker;
                    System.out.println("assignmentPath : " + assignmentPath);
                    createAssignment(assignmentPath, data);

                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.out.println(" DataCallback something wrong ");

        }

    };

    private void createAssignment(String path, byte[] data) {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, assignTaskcCallback, data);
    }

    AsyncCallback.StringCallback assignTaskcCallback = (int rc, String path, Object ctx, String name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                createAssignment(path, (byte[]) ctx);
                break;
            case OK:
                deleteTask(path);
                break;
            case NODEEXISTS:
                break;
            default:
                System.out.println("createAssignment something wrong");
        }

    };

    void deleteTask(String path) {
        Stat stat = new Stat();
        try {
            Thread.sleep(20000);
            zooKeeper.getData(path, false, stat);
            int version = stat.getVersion();
            System.out.println("path is : " + path + "  version is : " + version);
            zooKeeper.delete(path, version);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("this is begin");
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startzk();
        client.getTasks();


        while (true) {

        }
    }

}
