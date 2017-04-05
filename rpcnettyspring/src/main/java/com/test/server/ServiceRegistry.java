package com.test.server;

import com.google.common.base.Strings;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * Created by Administrator on 2017/4/5.
 */
public class ServiceRegistry {
    private String registryAddress;

    public ServiceRegistry(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    CuratorFramework connect() {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(registryAddress, retryPolicy);
        return client;
    }

    //创建临时节点的create 函数
    public void createEphemeral(CuratorFramework client, String path, byte[] data) {
        try {
            Stat stat = client.checkExists().forPath(path);

            if (stat == null) {
                if (Strings.isNullOrEmpty(data.toString())) {
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
                } else {
                    //value.getBytes(Charsets.UTF_8)
                    client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, data);

                }


            }

        } catch (Exception e) {
            System.out.println("---create fail");
            e.printStackTrace();
        }
    }

    public void register(String data) {
        CuratorFramework client = connect();
        client.start();
        createEphemeral(client, "/curatortest/create3", data.getBytes());
    }
}
