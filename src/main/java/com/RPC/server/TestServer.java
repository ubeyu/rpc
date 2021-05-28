package com.RPC.server;

import com.RPC.server.rpcServers.nettyServer.NettyRPCServer;
import com.RPC.server.rpcServers.simpleRPCServer.SimpleRPCServer;
import com.RPC.server.rpcServers.threadPoolRPCServer.ThreadPoolRPCServer;

import java.io.IOException;

/**
 * 更新1： HashMap<String, Object> 添加多个服务的实现类
 */

public class TestServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        UserServiceImpl usi = new UserServiceImpl();
        BlogServiceImpl bsi = new BlogServiceImpl();

//      写服务暴露类ServiceProvider，服务接口名是**直接手写的，其实可以利用class对象自动得到
//        Map<String, Object> serviceProvider = new HashMap<>();
//        serviceProvider.put("com.whyRPCver3.service.UserService", usi);
//        serviceProvider.put("com.whyRPCver3.service.BlogService", bsi);

        ServiceProvider sp = new ServiceProvider();
        sp.provideServiceInterface(usi);
        sp.provideServiceInterface(bsi);

        // 简单线程版本 RPC 服务端
        // RPCServer rpcServer = new SimpleRPCServer(sp);
        // 线程池版本 RPC 服务端
        // RPCServer rpcServer = new ThreadPoolRPCServer(sp);
        // Netty 版本 RPC 服务端
        RPCServer rpcServer = new NettyRPCServer(sp);
        rpcServer.start(2021);
    }
}
