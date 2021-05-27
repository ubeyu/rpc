package com.RPC.server;

import com.RPC.pojo.User;
import com.RPC.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        //根据id生成一个User并返回
        System.out.println("Client is Query ID:" + id + " User...");
        return new User(id, "zhouchunjie", false);
    }
}
