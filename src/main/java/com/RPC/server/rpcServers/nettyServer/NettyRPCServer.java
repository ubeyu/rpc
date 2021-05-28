package com.RPC.server.rpcServers.nettyServer;


import com.RPC.server.RPCServer;
import com.RPC.server.ServiceProvider;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty可以说是有Channel、EventLoop、ChannelFuture聚合起来的一个网络抽象代表
 *  1.Channel——Socket；
 *  2.EventLoop——控制流、多线程处理、并发
 *  3.ChannelFuture——异步通知
 */



public class NettyRPCServer implements RPCServer {

    private ServiceProvider serviceProvider;

    public NettyRPCServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        // Netty 服务端通常有两个线程组
        // Netty 的底层是IO多路复用
        // boss 为监听线程组，主要是监听客户端请求
        // work 为工作线程组，主要是处理与客户端的数据通讯
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        System.out.println("NettyRPCServer IS WORKING...");
        try{
            // 启动Netty服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 初始化服务器
            // serverBootstrap 的一些初始化参数：
            //     1.对应reactor线程模型的两个EventLoopGroup：bossGroup和workerGroup
            //     2.channel类型
            //     3.注入的handler
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new NettyRPCServerInitializer(serviceProvider));
            //同步阻塞，相当于socket绑定port
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //循环监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //关闭服务
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {

    }
}
