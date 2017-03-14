package curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * Created by root on 17-3-14.
 */
public class CurdTest {
    public static void create(CuratorFramework client, String path, byte[] payload) {
        try {
            client.create().forPath("/curatortest/create1", payload);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void createEphemeral(CuratorFramework client, String path, byte[] payload) {
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath("/curatortest/create2", payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String createEphemeralSequential(CuratorFramework client, String path, byte[] payload) throws Exception {
        return client.create()
                .withProtection()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/curatortest", payload);
    }

    public static void setData(CuratorFramework client, String path, byte[] payload) {
        try {
            client.setData().forPath("/curatortest/create1", "update data success".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDataAysn(CuratorFramework client, String path, byte[] payload) {
        try {
            CuratorListener listener = (CuratorFramework cf, CuratorEvent event) -> {
                switch (event.getType()) {
                    case CREATE:
                        System.out.println("create sucess");
                        break;
                    case SET_DATA:
                        System.out.println("event path: " + event.getPath());
                        System.out.println(event.toString());
                        cf.getData().watched().forPath(event.getPath());
                        System.out.println("update data aysn ");
                        break;
                }
            };
            client.getCuratorListenable().addListener(listener);
            client.setData()
                    .inBackground()
                    .forPath("/curatortest/create2", "update data success ".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDataAysnWithCallback(CuratorFramework client, BackgroundCallback callback, String path, byte[] payload) {
        //这是另外一种方法去异步获取通知
        //BackgroundCallback callback1=()->{};
        try {
            client.setData().inBackground(callback).forPath("/curatortest/create3-", "update data success".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(CuratorFramework client, String path) {
        try {
            client.delete().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guaranteedDelete(CuratorFramework client, String path) {
        try {
            client.delete().guaranteed().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> watchGetChildren(CuratorFramework client, String path) throws Exception {
        CuratorListener listener2 = (CuratorFramework cf, CuratorEvent event) -> {
            switch (event.getType()) {
                case DELETE:
                    System.out.println("node is deleted");
                    break;
                case CREATE:
                    System.out.println("node is created");
                    break;
                case CHILDREN:
                    System.out.println("node is change");
                    System.out.println(event.toString());
                    //watchGetChildren(cf, event.getPath());
                    break;
            }

        };

        PathChildrenCache childrenCache = new PathChildrenCache(client, "/curatortest", true);
        PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("开始进行事件分析:-----");
                ChildData data = event.getData();
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED : "+ data.getPath() +"  数据:"+ data.getData());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED : "+ data.getPath() +"  数据:"+ data.getData());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED : "+ data.getPath() +"  数据:"+ data.getData());
                        break;
                    default:
                        break;
                }
            }
        };
        //client.getCuratorListenable().addListener(listener2);
        childrenCache.getListenable().addListener(childrenCacheListener);
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        return client.getChildren().watched().forPath(path);
    }

    public static List<String> watchGetChildren(CuratorFramework client, String path, Watcher watcher) throws Exception {
        //Watcher watcher1=()->{};
        return client.getChildren().usingWatcher(watcher).forPath(path);
    }




    public static void main(String[] args) throws Exception {

        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();

        CurdTest.createEphemeral(client, "", "".getBytes());
        CurdTest.createEphemeralSequential(client, "", "".getBytes());
        CurdTest.setData(client, "", "".getBytes());
        CurdTest.setDataAysn(client, "", "".getBytes());
        //CurdTest.setDataAysnWithCallback(client,"","");
        CurdTest.watchGetChildren(client, "/curatortest");
        CurdTest.delete(client, "/curatortest/create1");
        CurdTest.create(client, "", "".getBytes());
        while (true) {

        }
    }
}