package com.RPC.server.rpcServers.threadPoolRPCServer;

import com.RPC.server.RPCServer;
import com.RPC.server.ServiceProvider;
import com.RPC.server.rpcServers.WorkThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRPCServer implements RPCServer {

    private final ThreadPoolExecutor threadPool;
    // 不直接保存map，改为保存ServiceProvider
    //  private Map<String, Object> serviceProvider;

    private ServiceProvider serviceProvider;

    //没有定义线程池参数，采用默认
    public ThreadPoolRPCServer(ServiceProvider serviceProvider) {
        threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                1000, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        this.serviceProvider = serviceProvider;
    }
    //定义线程池参数，采用输入参数
    public ThreadPoolRPCServer(ServiceProvider serviceProvider, int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> workQueue) {
        this.serviceProvider = serviceProvider;
        this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    public void start(int port){
        try{
            ServerSocket ss = new ServerSocket(port);
            System.out.println("ThreadPoolRPCServer IS WORKING...\n");
            while(true){
                Socket s = ss.accept();
                //启动线程池
                threadPool.execute(new WorkThread(s,serviceProvider));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ThreadPoolRPCServer ERROR!!!");
        }


    }

    @Override
    public void stop() {

    }
}