package com.RPC.server.rpcServers.nettyServer;


import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;
import com.RPC.server.ServiceProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//处理单元，处理请求
public class NettyRPCServerHandler extends SimpleChannelInboundHandler<RPCRequest> {

    private ServiceProvider serviceProvider;

//    private int opeCounts;

    public NettyRPCServerHandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
//        this.opeCounts = 0;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCRequest rpcRequest) throws Exception {
        // Netty服务端接收到请求
        System.out.println("Netty 服务端 Handler: 收到 RPCRequest ，正在处理...");
        RPCResponse rpcResponse = getResponse(rpcRequest);
        channelHandlerContext.writeAndFlush(rpcResponse);
        channelHandlerContext.close();
        // Netty服务端发送响应完毕
        System.out.println("Netty 服务端 Handler: 返回 RPCResponse 成功!!!\n");
    }

    //覆写捕获异常方法
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) throws Exception {
        cause.printStackTrace();;
        channelHandlerContext.close();
    }

    private RPCResponse getResponse(RPCRequest rpcRequest){
        //获取服务名
        String interfaceName = rpcRequest.getInterfaceName();
        //获取实现类
        Object service = serviceProvider.getService(interfaceName);
        //获取调用方法
        Method method = null;
        try{
            //得到调用方法
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamsTypes());
            //得到调用结果对象
            Object object = method.invoke(service, rpcRequest.getParams());
            //调用静态方法将object参数传入，得到RPCResponse
            return RPCResponse.sucess(object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("Netty 服务端 Handler: 处理 RPCRequest 失败!!!");
            //调用静态方法fail()，得到RPCResponse
            return RPCResponse.fail();
        }
    }
}
