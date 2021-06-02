package com.RPC.register;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * zookeeper服务注册接口的实现类
 */

public class ZKServiceRegister implements ServiceRegister {
    //curator是NetFlix做的zk框架，提供了zk的简单使用接口，这里初始化zk的客户端
    private CuratorFramework zkClient;
    //定义ZK路径的根节点
    private static final String ZK_ROOT_PATH = "whyRPC_ZK";

    //利用无参构造方法初始化zookeeper客户端，并与zookeeper服务端建立连接
    public ZKServiceRegister() {
        // 初始重试时间和次数
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 利用CuratorFrameworkFactory和builder初始化CuratorFramework
        // connectString输出了固定地址和端口，则在调用时不再变化
        // sessionTimeoutMs 与 zoo.cfg中的tickTime 有关系，
        // zk还会根据minSessionTimeout与maxSessionTimeout两个参数重新调整最后的超时值。
        // 默认分别为tickTime 的2倍和20倍
        // 使用心跳监听状态
        this.zkClient = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(2000).retryPolicy(retryPolicy).namespace(ZK_ROOT_PATH).build();
        //启动客户端
        this.zkClient.start();
        System.out.println("ZooKeeper注册中心：客户端初始化成功...\n");
    }

    //注册服务
    @Override
    public void registerService(String serviceName, InetSocketAddress serverAddress) {
        try {
            //检查当前服务名是否存在，不存在则创建成永久节点，服务提供者下线时，不删服务名，只删地址
            if(zkClient.checkExists().forPath("/"+serviceName) == null){
                //创建服务名，创建父节点
                zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/"+serviceName);
            }
            String serverAddressStr = parseToString(serverAddress);
            // 路径地址，一个/代表一个节点
            String path = "/" + serviceName + "/" + serverAddressStr;
            // 创建临时节点，服务器下线就删除节点
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            System.out.println("ZooKeeper注册中心：成功注册服务，服务名为 " + serviceName + "，地址为 " + serverAddressStr + "...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ZooKeeper注册中心：此服务已存在!!!");
        }
    }

    //查询服务
    @Override
    public InetSocketAddress getServiceAddressByServiceName(String serviceName) {
        try {
            //从zk客户端所有子节点中找到与服务名相同的存到list中
            List<String> serverNameList = zkClient.getChildren().forPath("/" + serviceName);
            //默认用第一个，后面会根据数量做负载均衡
            String serverAddressStr = serverNameList.get(0);
            return parseToAddress(serverAddressStr);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ZooKeeper注册中心：查询服务失败!!!");
            return null;
        }
    }

    //将 InetSocketAddress 转化为字符串形式的 “地址：端口号”
    private String parseToString(InetSocketAddress serverAddress){
        // 转化为"host:port" 格式
        return serverAddress.getHostName() + ":" + serverAddress.getPort();
    }

    //将字符串形式的 “地址：端口号” 转化为 InetSocketAddress
    private InetSocketAddress parseToAddress(String serverAddressStr){
        String[] serverAddress = serverAddressStr.split(":");
        return new InetSocketAddress(serverAddress[0], Integer.parseInt(serverAddress[1]));
    }
}
