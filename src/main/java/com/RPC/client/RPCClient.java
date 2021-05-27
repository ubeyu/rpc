package com.RPC.client;

import com.RPC.pojo.User;
import com.RPC.service.UserService;

public class RPCClient {
    public static void main(String[] args) {
        //得到服务端所用的接口实现proxy
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 2022);
        UserService proxy = clientProxy.getProxy(UserService.class);

        //调用1
        User user = proxy.getUserByUserId(949);
        System.out.println("查 ID 得到结果：" + user);

        //调用2
        User newUser = new User(231, "zhoucunjie2", true);
        Integer integer = proxy.insertUserId(newUser);
        System.out.println("插入 User 得到结果：" + integer);
    }
}

