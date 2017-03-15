package curator.framework;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by root on 17-3-14.
 */
public class CurdTest {

    // 这是framework create 函数
    public static void create(CuratorFramework client, String path, byte[] payload) {
        try {
            client.create().forPath("/curatortest/create1", payload);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //创建临时节点的create 函数
    public static void createEphemeral(CuratorFramework client, String path, byte[] payload) {
        try {
            client.create().withMode(CreateMode.EPHEMERAL).forPath("/curatortest/create2", payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //创建临时有序节点的函数
    public static String createEphemeralSequential(CuratorFramework client, String path, byte[] payload) throws Exception {
        return client.create()
                .withProtection()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath("/curatortest", payload);
    }

    //修改节点数据的函数
    public static void setData(CuratorFramework client, String path, byte[] payload) {
        try {
            client.setData().forPath("/curatortest/create1", "update data success".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改节点数据后，异步通知结果的函数
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

    //修改节点数据后，异步通知结果的函数
    public static void setDataAysnWithCallback(CuratorFramework client, String path, byte[] payload) {
        //这是另外一种方法去异步获取通知
        BackgroundCallback callback = (CuratorFramework clie, CuratorEvent event) -> {
            System.out.println("set Data aysn with callback");
        };
        try {
            client.setData().inBackground(callback).forPath("/curatortest/create3-", "update data success".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除函数
    public static void delete(CuratorFramework client, String path) {
        try {
            client.delete().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //保障性删除数据函数
    public void guaranteedDelete(CuratorFramework client, String path) {
        try {
            client.delete().guaranteed().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置监视点函数，只监听一次
    public static List<String> watchGetChildren(CuratorFramework client, String path) throws Exception {
        CuratorListener listener2 = (CuratorFramework cf, CuratorEvent event) -> {
            switch (event.getType()) {
                case DELETE:
                    System.out.println("node is deleted");
                    break;
                case CREATE:
                    System.out.println("node is created");
                    watchGetChildren(cf, "/curatortest");
                    break;
                case CHILDREN:
                    System.out.println("node is change");
                    System.out.println(event.toString());
                    //watchGetChildren(cf, event.getPath());
                    break;
            }

        };

        client.getCuratorListenable().addListener(listener2);
        return client.getChildren().watched().forPath(path);
    }

    //设置监听点函数，可以设置多次监听
    public static List<String> watchGetChildren(CuratorFramework client, String path, String string) throws Exception {
        Watcher watcher1 = (WatchedEvent event) -> {
            if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged) {

                System.out.println("watch get children method create node : " + event.getPath());
                try {
                    watchGetChildren(client, "/curatortest", " ");
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        };
        return client.getChildren().usingWatcher(watcher1).forPath(path);
    }

    //当不在需要关注此节点数据时,需要及时的关闭它.
    //因为每个cached,都会额外的消耗一个线程.
    //cached.close();////close the watcher,clear the cached Data

    /**
     * 1: Path Cache：监视一个路径下1）孩子结点的创建、2）删除，3）以及结点数据的更新。
     * 产生的事件会传递给注册的PathChildrenCacheListener。
     * 2: Node Cache：监视一个结点的创建、更新、删除，并将结点的数据缓存在本地。
     * 3: Tree Cache：Path Cache和Node Cache的“合体”，监视路径下的创建、更新、删除事件，并缓存路径下
     * <p/>
     * 这里实现的是path cache ：
     * 监听节点下变化：节点增加，节点删除，以及节点数据的更新，是重复监听，并且可以缓存数据
     * <p/>
     * 获取数据 even.getData().getPath()
     * 能监听所有的字节点 且是无限监听的模式 但是 指定目录下节点的子节点不再监听
     */
    public static void watchChange(CuratorFramework client) {
        PathChildrenCache childrenCache = new PathChildrenCache(client, "/curatortest", true);
        PathChildrenCacheListener childrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println("开始进行事件分析:-----");
                ChildData data = event.getData();
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("CHILD_ADDED : " + data.getPath() + "  数据:" + data.getData().toString());
                        break;
                    case CHILD_REMOVED:
                        System.out.println("CHILD_REMOVED : " + data.getPath() + "  数据:" + data.getData().toString());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("CHILD_UPDATED : " + data.getPath() + "  数据:" + data.getData().toString());
                        break;
                    default:
                        break;
                }
            }
        };
        childrenCache.getListenable().addListener(childrenCacheListener);
        try {
            //事件操作,将会在额外的线程中执行
            childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //2.Node Cache  监控本节点的变化情况   连接 目录 是否压缩
    //监听本节点的变化  节点可以进行修改操作  删除节点后会再次创建(空节点)
    private static void setListenterThreeTwo(CuratorFramework client) throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        //设置节点的cache
        final NodeCache nodeCache = new NodeCache(client, "/test", false);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("the test node is change and result is :");
                System.out.println("path : " + nodeCache.getCurrentData().getPath());
                System.out.println("data : " + new String(nodeCache.getCurrentData().getData()));
                System.out.println("stat : " + nodeCache.getCurrentData().getStat());
            }
        });
        nodeCache.start();
    }

    //3.Tree Cache
    // 监控 指定节点和节点下的所有的节点的变化--无限监听  可以进行本节点的删除(不在创建)
    private static void setListenterThreeThree(CuratorFramework client) throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        //设置节点的cache
        TreeCache treeCache = new TreeCache(client, "/test");
        //设置监听器和处理过程
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if (data != null) {
                    switch (event.getType()) {
                        case NODE_ADDED:
                            System.out.println("NODE_ADDED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;
                        case NODE_REMOVED:
                            System.out.println("NODE_REMOVED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;
                        case NODE_UPDATED:
                            System.out.println("NODE_UPDATED : " + data.getPath() + "  数据:" + new String(data.getData()));
                            break;

                        default:
                            break;
                    }
                } else {
                    System.out.println("data is null : " + event.getType());
                }
            }
        });
        //开始监听
        treeCache.start();
    }

    public static void main(String[] args) throws Exception {
        //创建client 方法1
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();

        CurdTest.createEphemeral(client, "", "".getBytes());
        CurdTest.createEphemeralSequential(client, "", "".getBytes());
        CurdTest.setData(client, "", "".getBytes());
        CurdTest.setDataAysn(client, "", "".getBytes());
        CurdTest.setDataAysnWithCallback(client, "", " ".getBytes());
        CurdTest.watchGetChildren(client, "/curatortest");
        CurdTest.delete(client, "/curatortest/create1");
        CurdTest.watchGetChildren(client, "/curatortest");
        CurdTest.watchGetChildren(client, "/curatortest", " ");
        CurdTest.create(client, "", "".getBytes());

        CurdTest.watchChange(client);

        CuratorFramework clientTwo = CurdTest.clientTwo();
        clientTwo.delete().forPath("/admin");
        //clientTwo.create().withMode(CreateMode.PERSISTENT).forPath("/admin"," test acl".getBytes());
        //System.out.println(clientTwo.getChildren().forPath("/"));
        while (true) {

        }
    }

    /**
     * @return void
     * @throws
     * @描述：创建一个zookeeper连接---连接方式二:优选这个
     */
    private static CuratorFramework clientTwo() {

        //默认创建的根节点是没有做权限控制的--需要自己手动加权限???----
        ACLProvider aclProvider = new ACLProvider() {
            private List<ACL> acl;

            @Override
            public List<ACL> getDefaultAcl() {
                if (acl == null) {
                    //获取一个acl list实例
                    ArrayList<ACL> acl = ZooDefs.Ids.CREATOR_ALL_ACL;
                    //清空里面的数据
                    acl.clear();
                    //往里面添加实例
                    acl.add(new ACL(ZooDefs.Perms.ALL, new Id("auth", "admin:admin")));
                    //acl.add(new ACL(ZooDefs.Perms.DELETE, new Id("auth", "root")));
                    this.acl = acl;
                }
                return acl;
            }

            @Override
            public List<ACL> getAclForPath(String path) {
                return acl;
            }
        };
        String scheme = "digest";
        byte[] auth = "admin:admin".getBytes();
        int connectionTimeoutMs = 5000;
        String connectString = "127.0.0.1:2181";
        String namespace = "testacl";
        CuratorFramework client = CuratorFrameworkFactory.builder().aclProvider(aclProvider).
                authorization(scheme, auth).
                connectionTimeoutMs(connectionTimeoutMs).
                connectString(connectString).
                namespace(namespace).
                retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).build();
        client.start();
        return client;
    }
}