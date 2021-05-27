package com.RPC.server;


import com.RPC.pojo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserServiceImpl usi = new UserServiceImpl();
        try {
            ServerSocket ss = new ServerSocket(2021);
            System.out.println("RPCServer IS WORKING!!!");
            while(true){
                Socket s = ss.accept();
                new Thread(()->{
                    try {
                        //读，读传入ID
                        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                        //写，写传出User
                        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                        Integer id = ois.readInt();
                        System.out.println("ID Received!!!");
                        //根据接口实现查User
                        User user = usi.getUserByUserId(id);
                        //写入并传回
                        oos.writeObject(user);
                        oos.flush();

                    } catch (IOException e) {
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
