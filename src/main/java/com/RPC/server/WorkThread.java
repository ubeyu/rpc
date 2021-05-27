package com.RPC.server;

import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 这里负责解析得到的request请求，执行服务方法，返回给客户端
 * 1. 从request得到interfaceName
 * 2. 根据interfaceName在serviceProvide Map中获取服务端的实现类
 * 3. 从request中得到方法名，参数， 利用反射执行服务中的方法
 * 4. 得到结果，封装成response，写入socket
 */

public class WorkThread implements Runnable{
    private Socket s;

    // 不直接保存map，改为保存ServiceProvider
    // private Map<String, Object> serviceProvider;
    private ServiceProvider serviceProvider;

    public WorkThread(Socket s, ServiceProvider serviceProvider) {
        this.s = s;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void run() {
        try {
            //读，读传入RPCRequest
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            //写，写传出User
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

            RPCRequest rpcRequest = (RPCRequest) ois.readObject();
            System.out.println("RPCRequest Received!!!");

            //调用getResponse获取RPCResponse，写入输出流并传回
            RPCResponse rpcResponse = getResponse(rpcRequest);
            oos.writeObject(rpcResponse);
            oos.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("IO ERROR!!!");
        }
    }


    //----------------------------------核心---------------------------------------------
    //根据rpcRequest提供的方法名和参数类型找到方法，利用反射调用该方法
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
            System.out.println("RPCServer IS NOT WORKING!!!");
            //调用静态方法fail()，得到RPCResponse
            return RPCResponse.fail();
        }
    }
    //-----------------------------------------------------------------------------------
}
