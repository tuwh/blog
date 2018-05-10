package com.uncub.framework.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 提供分布式锁的服务
 */
@Service
public class LockHelper {
    @Autowired
    private CuratorFramework curatorClient;
    private String machineName;
    CuratorFrameworkFactory curatorFrameworkFactory;

    Logger logger = LoggerFactory.getLogger(LockHelper.class.getName());

    {
        try {
            machineName = InetAddress.getLocalHost().getHostName();
            System.out.println(machineName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在有获取到分布式锁后执行指定的回调方法
     * 此方法在指定时间一直阻塞，直到获取到锁
     * 超出指定时间未获取到锁，抛出异常，回调不执行
     *
     * @param time     指定的阻塞时间
     * @param callBack 回调方法
     * @return callBack 的返回值
     * @throws Exception
     */
    public <T> T doWithLock(int time, ZookeeperCallBack<T> callBack) throws Exception {
        InterProcessMutex lock = null;
        try {
            lock = new InterProcessMutex(curatorClient, "/test_group");
            logger.debug("服务器【" + machineName + "】线程开始尝试获取锁");
            if (lock.acquire(time, TimeUnit.SECONDS)) {
                logger.debug("第" + machineName + "次获取到锁");
                return callBack.call();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (lock != null && lock.isAcquiredInThisProcess()) {
                lock.release();
                logger.debug("服务器【" + machineName + "】次释放了锁");
            }
            if (curatorClient != null) {
                curatorClient.close();
            }
        }
        throw new Exception("未在指定时间内获取到锁，任务未执行！");
    }

    /**
     * 在有获取到分布式锁后执行指定的回调方法
     * 此方法一直阻塞，知道获取到锁
     *
     * @param callBack 回调方法
     * @return callBack 的返回值
     * @throws Exception
     */
    public <T> T doWithLock(ZookeeperCallBack<T> callBack) throws Exception {
        return doWithLock(-1, callBack);
    }

    /**
     * 选举，选举成功后执行回调方法，回调方法释放后会释放当前的leader
     * @param path
     * @param callBack 选举成功后执行回调方法
     * @throws InterruptedException
     */
    public void selectLeader(String path, final ZookeeperCallBack callBack) throws InterruptedException {

        LeaderSelector selector = new LeaderSelector(curatorClient, path, new LeaderSelectorListenerAdapter() {

            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println("成为Master角色");
                callBack.call();
                System.out.println("完成Master操作，释放Master权利");
            }
        });
        selector.autoRequeue();
        selector.start();
    }


}
