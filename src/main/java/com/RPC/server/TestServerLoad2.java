package com.RPC.server;

import com.RPC.server.rpcServers.nettyServer.NettyRPCServer;

import java.io.IOException;

/**
 * 更新1： HashMap<String, Object> 添加多个服务的实现类
 */

public class TestServerLoad2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        UserServiceImpl usi = new UserServiceImpl();
        BlogServiceImpl bsi = new BlogServiceImpl();

//      写服务暴露类ServiceProvider，服务接口名是**直接手写的，其实可以利用class对象自动得到
//        Map<String, Object> serviceProvider = new HashMap<>();
//        serviceProvider.put("com.whyRPCver3.service.UserService", usi);
//        serviceProvider.put("com.whyRPCver3.service.BlogService", bsi);

        // 服务暴露类ServiceProvider初始化的端口号 需要与 RPC服务启动的端口号 相同
        // 客户端不再需要填写端口号，统一从ZKServiceRegister获取 2181 端口号
        ServiceProvider sp = new ServiceProvider("127.0.0.1", 2023);
        sp.provideServiceInterface(usi);
        sp.provideServiceInterface(bsi);

        // 简单线程版本 RPC 服务端
        // RPCServer rpcServer = new SimpleRPCServer(sp);
        // 线程池版本 RPC 服务端
        // RPCServer rpcServer = new ThreadPoolRPCServer(sp);
        // Netty 版本 RPC 服务端
        RPCServer rpcServer = new NettyRPCServer(sp);

        // 启动服务端
        rpcServer.start(2023);
    }
}
