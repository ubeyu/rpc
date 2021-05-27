package com.RPC.client;

import com.RPC.pojo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RPCClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 2021);

            //客户端在写Socket时，应先定义输出流，若不输出，不可能返回输入
            //发，发id号
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            //收，接收User
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            //写入id号并发出
            oos.writeInt(949);
            oos.flush();

            //接收
            User user = (User) ois.readObject();
            System.out.println("Server Response：" + user);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("RPCClient IS NOT WORKING!!!");
        }
    }
}
