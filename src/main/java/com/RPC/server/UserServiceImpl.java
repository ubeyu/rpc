package com.RPC.server;


import com.RPC.pojo.User;
import com.RPC.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        //根据id生成一个User并返回
        System.out.println("实现类：客户端正在查询 ID 为 " + id + " 的用户...");
        return new User(id, "zhouchunjie", false);
    }

    @Override
    public Integer insertUserId(User user) {
        //打印增加数据，并返回1
        System.out.println("实现类：客户端正在新建用户: " + user + " ...");
        return 1;
    }
}
