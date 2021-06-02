package com.RPC.server;

import com.RPC.register.ServiceRegister;
import com.RPC.register.ZKServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 存放服务接口名与服务端对应的实现类
 * 根据request中的interface调用服务端中相关实现类
 * 第六次提交更新：引入了ZK注册中心
 */

public class ServiceProvider {
    private Map<String, Object> interfaceProvider;

    //第六次提交更新：引入注册中心，将服务端自身的地址和端口号存入注册中心
    private ServiceRegister serviceRegister;
    private String host;
    private int port;

    //调用构造方法生成实体类
    //传入地址和端口号存入注册中心
    public ServiceProvider(String host, int port) {
        this.interfaceProvider = new HashMap<>();
        this.serviceRegister = new ZKServiceRegister();
        this.host = host;
        this.port = port;
    }

    //根据实现类存放其所有方法
    public void provideServiceInterface(Object service){

        //根据接口的实现类获取服务名
        // String serviceName = service.getClass().getName();

        //获取该实现类的所有方法
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for(Class clazz : interfaces){
            // 本机的方法名和服务名的映射表
            interfaceProvider.put(clazz.getName(), service);
            // 直接在存方法时将服务注册
            serviceRegister.registerService(clazz.getName(), new InetSocketAddress(host, port));
        }
    }

    //获取实现类的所有方法
    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}