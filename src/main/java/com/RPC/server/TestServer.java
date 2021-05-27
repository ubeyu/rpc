package com.RPC.server;

import java.io.IOException;

/**
 * 更新1： HashMap<String, Object> 添加多个服务的实现类
 */

public class TestServer {
    public static void main(String[] args) throws IOException {
        UserServiceImpl usi = new UserServiceImpl();
        BlogServiceImpl bsi = new BlogServiceImpl();

//      写服务暴露类ServiceProvider，服务接口名是**直接手写的，其实可以利用class对象自动得到
//        Map<String, Object> serviceProvider = new HashMap<>();
//        serviceProvider.put("com.whyRPCver3.service.UserService", usi);
//        serviceProvider.put("com.whyRPCver3.service.BlogService", bsi);

        //初始化ServiceProvider
        ServiceProvider sp = new ServiceProvider();
        sp.provideServiceInterface(usi);
        sp.provideServiceInterface(bsi);

        //两种方法开启RPCServer
//        RPCServer rpcServer = new SimpleRPCServer(sp);
        RPCServer rpcServer = new ThreadPoolRPCServer(sp);

        rpcServer.start(2021);
    }
}
