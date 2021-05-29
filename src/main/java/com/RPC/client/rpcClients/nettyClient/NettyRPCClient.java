package com.RPC.client.rpcClients.nettyClient;


import com.RPC.client.RPCClient;
import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;


public class NettyRPCClient implements RPCClient {

    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;

    private String host;
    private int port;

    public NettyRPCClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    // netty客户端初始化，重复使用
    // 只创建了一个NioEventLoopGroup实例
    // 客户端并不需要使用I/O多路复用模型
    // 需要有一个Reactor来接受请求，只需要单纯的读写数据即可
    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new NettyRPCClientInitializer());
    }


    // 这里需要操作一下，因为netty的传输都是异步的，你发送request，会立刻返回一个值， 而不是想要的相应的response


    @Override
    public RPCResponse sendRPCRequest(RPCRequest rpcRequest) {
        try {
            //Socket s = new Socket(host, port);
            //ChannelFuture 替代上面 Socket 语句
            ChannelFuture channelFuture  = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();

            //            //客户端在写Socket时，应先定义输出流，若不输出，不可能返回输入
            //            //发，发RPCRequest
            //            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            //            //收，接RPCResponse
            //            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            //
            //            //写入rpcRequest号并发出
            //            oos.writeObject(rpcRequest);
            //            oos.flush();
            // 发送请求数据给 channel，代替上面语句
            channel.writeAndFlush(rpcRequest);
            channel.closeFuture().sync();

            // 阻塞的获得结果，通过给channel设计别名，获取特定名字下的channel中的内容（这个在hanlder中设置）
            // AttributeKey是，线程隔离的，不会由线程安全问题。
            // 实际上不应通过阻塞，可通过回调函数
            AttributeKey<RPCResponse> attributeKey = AttributeKey.valueOf("RPCResponse");
            RPCResponse rpcResponse = channel.attr(attributeKey).get();

            //返回 rpcResponse
            return rpcResponse;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("NettyRPCClient ERROR!!!");
            return null;
        }
    }
}