package com.RPC.server.rpcServers.nettyServer;

import com.RPC.selfCode.SelfDecode;
import com.RPC.selfCode.SelfEncode;
import com.RPC.selfCode.serializer.ObjectSerializer;
import com.RPC.server.ServiceProvider;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
// Netty自带的编解码器
//import io.netty.handler.codec.serialization.ObjectDecoder;
//import io.netty.handler.codec.serialization.ObjectEncoder;
//import io.netty.handler.codec.serialization.ClassResolver;

//通道初始化器ChannelInitializer
public class NettyRPCServerInitializer extends ChannelInitializer<SocketChannel> {

    private ServiceProvider serviceProvider;

    public NettyRPCServerInitializer() {
    }

    public NettyRPCServerInitializer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        //在处理器链的最后添加一些处理单元
        ChannelPipeline pipeline = socketChannel.pipeline();


        // Netty自带的编解码器

//        // 消息格式 [长度][消息体], 解决TCP连接粘包问题
//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0 , 4, 0 , 4));
//        // 计算当前待发送消息的长度，写入到前4个字节中
//        pipeline.addLast(new LengthFieldPrepender(4));

//        pipeline.addLast(new ObjectEncoder());
//        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
//            @Override
//            public Class<?> resolve(String className) throws ClassNotFoundException {
//                return Class.forName(className);
//            }
//        }));

        // 自定义编解码器
        pipeline.addLast(new SelfDecode());

        //Java 原生序列化
        //pipeline.addLast(new SelfEncode(new ObjectSerializer()));
        //Json 序列化
        pipeline.addLast(new SelfEncode(new ObjectSerializer()));

        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}