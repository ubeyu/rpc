package com.RPC.server;


import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserServiceImpl usi = new UserServiceImpl();
        try {
            ServerSocket ss = new ServerSocket(2022);
            System.out.println("RPCServer IS WORKING...");
            while(true){
                Socket s = ss.accept();
                new Thread(()->{
                    try {
                        //读，读传入RPCRequest
                        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                        //写，写传出User
                        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                        RPCRequest rpcRequest = (RPCRequest) ois.readObject();
                        System.out.println("rpcRequest Received!!!");

                        //----------------------------------核心---------------------------------------------
                        //根据rpcRequest提供的方法名和参数类型找到方法，利用反射调用该方法
                        Method method = usi.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamsTypes());
                        Object object = method.invoke(usi, rpcRequest.getParams());

                        //写入RPCResponse并传回
                        oos.writeObject(RPCResponse.sucess(object));
                        oos.flush();
                        //-----------------------------------------------------------------------------------

                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        System.out.println("IO ERROR!!!");
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("RPCServer IS NOT WORKING!!!");
        }
    }
}

