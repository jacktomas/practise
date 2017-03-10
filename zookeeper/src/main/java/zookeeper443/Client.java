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

    //1：设置监视点，监视/tasks 是否发生变化
    void getTasks() {
        zooKeeper.getChildren("/tasks", taskChangeWatcher, taskGetChildrenCallback, null);
    }

    Watcher taskChangeWatcher = (WatchedEvent e) -> {
        System.out.println(" get task change " + e.getPath());
        if (e.getType() == Event.EventType.NodeChildrenChanged) {
            assert "/tasks".equals(e.getPath());
            //2: 发生变化后继续监视
            getTasks();
        }
    };
    //3：变化后，调用回调函数处理
    AsyncCallback.ChildrenCallback taskGetChildrenCallback = (int rc, String path, Object ctx, List<String> children) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                getTasks();
                break;
            case OK:
                if (children != null) {
                    System.out.println(" get children :" + children);
                    //4：变化后,获得任务信息
                    assginTasks(children);
                }
                break;
            default:
                System.out.println("this is other error");
        }
    };

    private void assginTasks(List<String> tasks) {
        for (String task : tasks) {
            //5：获取任务信息
            getTaskData(task);
        }
    }

    private void getTaskData(String task) {
        System.out.println("getTaskData :" + task);
        zooKeeper.getData("/tasks/" + task, false, taskDataCallback, task);
    }

    //6：获取任务信息成功后，调用回调函数
    AsyncCallback.DataCallback taskDataCallback = (int rc, String path, Object ctx, byte[] data, Stat stat) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                getTaskData((String) ctx);
                break;
            case OK:
                try {
                    //7：这里是随机分配任务，这里还没写完善
                    List<String> children = zooKeeper.getChildren("/tasks", this);
                    Random random = new Random();
                    int i = random.nextInt(children.size());
                    String designateworker = children.get(i);
                    System.out.println("designateworker:  " + designateworker);
                    String assignmentPath = "/assign/" + designateworker;
                    System.out.println("assignmentPath : " + assignmentPath);
                    //8：完成随机分配任务后，进行任务创建分发
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

    //9：进行任务分发，创建路径
    private void createAssignment(String path, byte[] data) {
        zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, assignTaskcCallback, data);
    }

    //10：分发完成后调用回调函数，善后工作
    AsyncCallback.StringCallback assignTaskcCallback = (int rc, String path, Object ctx, String name) -> {
        switch (KeeperException.Code.get(rc)) {
            case CONNECTIONLOSS:
                createAssignment(path, (byte[]) ctx);
                break;
            case OK:
                System.out.println(" will be delete data: ");
                //11：删除已分发的任务
                deleteTask(path);
                break;
            case NODEEXISTS:
                break;
            default:
                System.out.println("createAssignment something wrong");
        }
    };

    //12 ：删除函数
    void deleteTask(String path) {
        Stat stat = new Stat();
        try {
            //剪切 /assign/task-X 中的 task-x 与 /tasks/ 拼接成 /tasks/task-x
            String taskPath = "/tasks/" + path.substring(path.lastIndexOf("/") + 1);
            System.out.println("will to delete path is : " + taskPath);
            //获取数据状态信息
            zooKeeper.getData(taskPath, false, stat);
            //获取数据版本号
            int version = stat.getVersion();
            System.out.println("path is : " + path + "  version is : " + version);
            //根据路径以及版本号删除节点
            zooKeeper.delete(taskPath, version);
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
