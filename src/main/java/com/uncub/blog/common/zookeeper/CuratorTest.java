package com.uncub.blog.common.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:spring-application.xml"}) //加载配置文件
public class CuratorTest {
    private static AtomicInteger count = new AtomicInteger(0);
    @Test
    public void test() {
        String address = "192.168.126.128:2181";
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(1000, 3));
        try {
            client.start();
            String path = "/abcd";
            Stat stat = client.checkExists().forPath(path);
            if (stat == null) {
                client.create()
                        .creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(path, "hello, zk".getBytes());
                System.out.println("创建成功！ path = " + path);
            } else {
                byte[] data = client.getData().forPath(path);
                System.out.println("节点数据" + new String(data, Charset.forName("utf8")));
                System.out.println("开始修改节点");
                System.out.println(stat.getVersion());
                client.setData().forPath(path, "hello2,zoo".getBytes());
                System.out.println("修改数据后版本" + stat.getVersion());
                stat = client.checkExists().forPath("/abcd");
                System.out.println("修改数据后版本(重新获取)" + stat.getVersion());
                data = client.getData().forPath(path);
                System.out.println("节点数据" + new String(data, Charset.forName("utf8")));
                System.out.println("删除节点");
                client.delete().guaranteed().forPath(path);
                stat = client.checkExists().forPath("/abcd");
                if (stat == null) System.out.println("删除成功！");
                else System.out.println("删除失败！");
//                stat.getMzxid();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null)
                client.close();
        }
    }

    @Test
    public void testNodeWatch() throws Exception {
        String address = "192.168.126.128:2181";
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(1000, 3));
        client.start();
        String path = "/abcd";
        final NodeCache nodeCache = new NodeCache(client, path);
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("变化后的值：" + new String(nodeCache.getCurrentData().getData(), Charset.forName("utf8")));
            }
        });

        Thread.sleep(1000000);
    }

    @Test
    public void testPathWatch() throws Exception {
        String address = "192.168.126.128:2181,192.168.126.128:2181";
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(1000, 3));
        client.start();
        String path = "/abcd";
        final PathChildrenCache nodeCache = new PathChildrenCache(client, path, true);
        nodeCache.start();
        nodeCache.getListenable().addListener(new PathChildrenCacheListener() {
            /**
             * Called when a change has occurred
             *
             * @param client the client
             * @param event  describes the change
             * @throws Exception errors
             */
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                switch (event.getType()) {
                    case CHILD_ADDED:
                        System.out.println("新增：" + new String(event.getData().getData()));
                        break;
                    case CHILD_REMOVED:
                        System.out.println("删除：" + event.getData().getPath());
                        break;
                    case CHILD_UPDATED:
                        System.out.println("修改：" + new String(event.getData().getData()));
                        break;
                    default:
                        break;
                }
            }
        });

        Thread.sleep(1000000);
    }

    @Test
    public void testACL() throws Exception {
        RetryPolicy retry = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.126.128:2181", 5000, 5000, retry);
        client.start();

        //ACL有IP授权和用户名密码访问的模式
        ACL aclRoot = new ACL(ZooDefs.Perms.ALL,new Id("digest",DigestAuthenticationProvider.generateDigest("root:root")));
        List<ACL> aclList = new ArrayList<ACL>();
        aclList.add(aclRoot);

        String path = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .withACL(aclList)
                .forPath("/node_3/node_ACL", "234".getBytes());
        System.out.println(path);

        CuratorFramework client1 =  CuratorFrameworkFactory.builder().connectString("192.168.126.128:2181")
                .sessionTimeoutMs(5000)//会话超时时间
                .connectionTimeoutMs(5000)//连接超时时间
                .authorization("digest","root:root".getBytes())//权限访问
                .retryPolicy(retry)
                .build();

        client1.start();
        String re = new String(client1.getData().forPath(path));
        System.out.println(re);
        Thread.sleep(100000000);
    }

    @Test
    public void testGetdataInBackground() throws Exception {
        CuratorFramework client = null;
        try {
            String address = "192.168.126.128:2181,192.168.126.128:2181";
            client = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(1000, 3));
            client.start();
            String path = "/abc";
            System.out.println("节点"+ client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, "abc".getBytes()));
            System.out.println("节点" + client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, "abc".getBytes()));
            String path1 = client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, "abc".getBytes());
            Stat stat = client.checkExists().forPath(path1);
            System.out.println(stat == null);
            byte [] bytes = client.getData().forPath(path1);
            System.out.println(new String (bytes));
            client.usingNamespace("abc").create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/abc");
            Thread.sleep(10000000);
        }catch (Exception e){
            throw e;
        }finally {
            if (client != null) {
                client.close();
            }
        }
    }

    public static void synAdd() throws Exception {
        CuratorFramework client = null;
        InterProcessMutex lock = null;
        String address = "192.168.126.128:2181,192.168.126.128:2181";
        String plusNode = "/plus";
        int currentCount = count.addAndGet(1);
        try{
            client = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(1000, 3));
            client.start();
            lock = new InterProcessMutex(client, "/test_group");
            System.out.println("第 "+ currentCount + "线程尝试获取锁");
            if (lock.acquire(30,TimeUnit.SECONDS)){
               /* Stat stat = client.checkExists().forPath(plusNode);
                if (stat == null){
                    client.create().withMode(CreateMode.PERSISTENT).forPath(plusNode, "0".getBytes());
                }*/
                System.out.println("第 "+ currentCount + "次获取到锁");
                byte[] bytes = client.getData().forPath(plusNode);
                String res = new String(bytes);
                System.out.println(res);
                int currentSum = Integer.valueOf(res);
                currentSum ++;
                client.setData().forPath(plusNode, String.valueOf(currentSum).getBytes());
            }
        }catch (Exception e){
//            e.printStackTrace();
            throw e;
        }finally {
            if (lock != null && lock.isAcquiredInThisProcess()) {
                lock.release();
                System.out.println("第 "+ currentCount + "次释放了锁");
            }

            if (client != null) {
                client.close();
            }

        }

    }


    @Test
    public void testLock() throws Exception {
        String address = "192.168.126.128:2181,192.168.126.128:2181";
        String plusNode = "/plus";
        CuratorFramework client = CuratorFrameworkFactory.newClient(address, new ExponentialBackoffRetry(1000, 3));
        client.start();
        Stat stat = client.checkExists().forPath(plusNode);
        if (stat == null){
            client.create().withMode(CreateMode.PERSISTENT).forPath(plusNode, "0".getBytes());
        }
        client.close();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final AtomicInteger integer = new AtomicInteger(0);
        long startTime = System.currentTimeMillis();
        for (int i=0;i<2000;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run(){
                    try {
//                        System.out.println(integer.addAndGet(1));
                        synAdd();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        if(executorService.awaitTermination(100000,TimeUnit.SECONDS)){
            executorService.shutdownNow();
        };
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime-startTime)/1000);
    }
}
