package com.RPC.client;


import com.RPC.pojo.Blog;
import com.RPC.pojo.User;
import com.RPC.service.BlogService;
import com.RPC.service.UserService;

public class RPCClient {
    public static void main(String[] args) {
        //得到服务端所用的接口实现proxy
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 2021);

        UserService userProxy = clientProxy.getProxy(UserService.class);
        BlogService blogProxy = clientProxy.getProxy(BlogService.class);

        //调用1
        User user = userProxy.getUserByUserId(949);
        System.out.println("查 User ID 得到结果：" + user);

        //调用2
        User newUser = new User(231, "zhoucunjie2", true);
        Integer integer = userProxy.insertUserId(newUser);
        System.out.println("插入新 User 得到结果：" + integer);

        //调用3
        Blog blog = blogProxy.getBlogById(949);
        System.out.println("查 Blog ID 得到结果：" + blog);
    }
}
