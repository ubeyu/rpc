package com.RPC.client.rpcClients.SimpleClient;



import com.RPC.client.RPCClient;
import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;
import com.RPC.register.ServiceRegister;
import com.RPC.register.ZKServiceRegister;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SimpleRPCClient implements RPCClient {
    private String host;
    private int port;
    //第六次提交，引入注册中心
    private ServiceRegister serviceRegister;

    public SimpleRPCClient() {
        //第六次提交，不需要再从构造函数传入host和port，初始化zk注册中心
        //this.host = host;
        //this.port = port;
        this.serviceRegister = new ZKServiceRegister();
    }

    public RPCResponse sendRPCRequest(RPCRequest rpcRequest) {
        //第六次提交，根据请求的接口名，查询zk注册中心中服务的host和port
        InetSocketAddress serviceAddress = serviceRegister.getServiceAddressByServiceName(rpcRequest.getInterfaceName());
        //地址转化成 host 和 port
        host = serviceAddress.getHostName();
        port = serviceAddress.getPort();
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
            System.out.println("SimpleRPCClient ERROR!!!");
            return null;
        }
    }
}