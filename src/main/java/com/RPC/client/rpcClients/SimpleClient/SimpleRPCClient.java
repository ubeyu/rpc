package com.RPC.client.rpcClients.SimpleClient;



import com.RPC.client.RPCClient;
import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SimpleRPCClient implements RPCClient {
    private String host;
    private int port;

    public SimpleRPCClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RPCResponse sendRPCRequest(RPCRequest rpcRequest) {
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
            System.out.println("RPCClient ERROR!!!");
            return null;
        }
    }
}
