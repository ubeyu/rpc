package com.RPC.client;

import com.RPC.client.rpcClients.SimpleClient.SimpleRPCClient;
import com.RPC.client.rpcClients.nettyClient.NettyRPCClient;
import com.RPC.pojo.Blog;
import com.RPC.pojo.User;
import com.RPC.service.BlogService;
import com.RPC.service.UserService;

public class TestClient {
    public static void main(String[] args) {
        // 构建一个使用java Socket/ netty/....传输的客户端

        // 得到服务端所用的接口实现proxy
        //SimpleRPCClient simpleRPCClient = new SimpleRPCClient("127.0.0.1", 2021);
        //NettyRPCClient nettyRPCClient = new NettyRPCClient("127.0.0.1", 2021);

        //第六次提交，不需要再从构造函数传入host和port，初始化zk注册中心
        // 客户端不再需要填写端口号，统一从ZKServiceRegister获取 2181 端口号
        //SimpleRPCClient simpleRPCClient = new SimpleRPCClient();
        NettyRPCClient nettyRPCClient = new NettyRPCClient();

        ClientProxy clientProxy = new ClientProxy(nettyRPCClient);

        UserService userProxy = clientProxy.getProxy(UserService.class);
        BlogService blogProxy = clientProxy.getProxy(BlogService.class);

        // 调用1
        User user = userProxy.getUserByUserId(949);
        System.out.println("客户端：查 User ID 得到结果：" + user + "\n");

        // 调用2
        User newUser = new User(231, "zhoucunjie2", true);
        Integer integer = userProxy.insertUserId(newUser);
        System.out.println("客户端：插入新 User 得到结果：" + integer + "\n");

        // 调用3
        Blog blog = blogProxy.getBlogById(10000);
        System.out.println("客户端：查 Blog ID 得到结果：" + blog + "\n");
    }
}
