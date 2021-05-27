package com.RPC.service;

import com.RPC.pojo.User;

public interface UserService {
    //客户端通过此接口得到实现类
    User getUserByUserId(Integer id);
}
