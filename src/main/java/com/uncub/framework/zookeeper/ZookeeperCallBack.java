package com.uncub.framework.zookeeper;

/**
 * zookeeper回调类
 * @param <T>
 */
public interface ZookeeperCallBack<T> {
    T call();
}
