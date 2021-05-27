package com.RPC.client;

import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 负责底层与服务端的通信，客户端发起一次请求调用，Socket建立连接，发送Request，接收Response
 * 这里Request是封装好的（上层进行封装），不同的service需要进行不同的封装
 * 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
 */

public class IOClient {
    public static RPCResponse sendRequest(String host, int port, RPCRequest rpcRequest){
        try {
            Socket s = new Socket(host, port);

            //客户端在写Socket时，应先定义输出流，若不输出，不可能返回输入
            //发，发RPCRequest
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            //收，接RPCResponse
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            //写入rpcRequest号并发出
            oos.writeObject(rpcRequest);
            oos.flush();

            //接收并返回 rpcResponse
            return (RPCResponse) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("RPCClient IS NOT WORKING!!!");
            return null;
        }
    }
}
