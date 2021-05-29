package com.RPC.server;

import com.RPC.pojo.Blog;
import com.RPC.service.BlogService;

public class BlogServiceImpl implements BlogService {

    @Override
    public Blog getBlogById(Integer id) {
        System.out.println("实现类：客户端正在查询 ID 为 " + id + " 的博客...");
        return new Blog(id, 1, "zhouchunjiesile!");
    }
}
