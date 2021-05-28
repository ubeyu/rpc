package com.RPC.client.rpcClients.nettyClient;

import com.RPC.pojo.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class NettyRPCClientHandler extends SimpleChannelInboundHandler<RPCResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCResponse rpcResponse) throws Exception {
        // 接收到response, 给channel设计别名，让sendRequest里读取response
        AttributeKey<RPCResponse> attributeKey = AttributeKey.valueOf("RPCResponse");
        channelHandlerContext.channel().attr(attributeKey).set(rpcResponse);
        channelHandlerContext.channel().close();
    }
    // 覆写捕获异常方法
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        cause.printStackTrace();;
        channelHandlerContext.close();
    }
}
