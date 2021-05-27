package com.RPC.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleRPCServer implements RPCServer{

    // 不直接保存map，改为保存ServiceProvider
    //  private Map<String, Object> serviceProvider;

    private ServiceProvider serviceProvider;

    public SimpleRPCServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try{
            ServerSocket ss = new ServerSocket(port);
            System.out.println("SimpleRPCServer IS WORKING...");
            while(true){
                Socket s = ss.accept();
                new Thread(new WorkThread(s,serviceProvider)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("SimpleRPCServer ERROR!!!");
        }
    }

    @Override
    public void stop() {

    }
}
