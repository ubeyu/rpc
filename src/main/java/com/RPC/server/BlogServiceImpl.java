package com.RPC.server;

import com.RPC.pojo.Blog;
import com.RPC.service.BlogService;

public class BlogServiceImpl implements BlogService {

    @Override
    public Blog getBlogById(Integer id) {
        System.out.println("Client is Query ID:" + id + " Blog...");
        return new Blog(id, 1, "zhouchunjiesile!");
    }
}
