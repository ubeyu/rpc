package com.RPC.client;


import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理封装request对象
 * InvocationHandler接口是proxy代理实例的调用处理程序实现的一个接口，每一个proxy代理实例都有一个关联的调用处理程序；在代理实例调用方法时，方法调用被编码分派到调用处理程序的invoke方法
 */


public class ClientProxy implements InvocationHandler {
//    第四次更新，由于写了RPCClient，包含host和port，则代替这部分
//    private String host;
//    private int port;
//
//    public ClientProxy(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }
    private RPCClient rpcClient;

    public ClientProxy(RPCClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    // jdk 动态代理， 每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //构建RPCRequest
        RPCRequest rpcRequest = new RPCRequest();
        rpcRequest.setInterfaceName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParams(args);
        rpcRequest.setParamsTypes(method.getParameterTypes());
        //得到
        RPCResponse rpcResponse = rpcClient.sendRPCRequest(rpcRequest);
        return rpcResponse.getObject();
    }
    <T>T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}