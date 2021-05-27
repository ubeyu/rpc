package com.RPC.service;

import com.RPC.pojo.Blog;

public interface BlogService {
    //客户端通过此接口得到Blog类
    Blog getBlogById(Integer id);
}
