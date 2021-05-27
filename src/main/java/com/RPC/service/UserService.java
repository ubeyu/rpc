package com.RPC.service;

import com.RPC.pojo.User;

public interface UserService {
    //客户端通过此接口得到实现类
    User getUserByUserId(Integer id);
    //根据User增加数据，返回Integer
    Integer insertUserId(User user);
}
