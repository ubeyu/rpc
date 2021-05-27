package com.RPC.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放服务接口名与服务端对应的实现类
 * 根据request中的interface调用服务端中相关实现类
 */

public class ServiceProvider {
    private Map<String, Object> interfaceProvider;

    //调用构造方法生成实体类
    public ServiceProvider() {
        this.interfaceProvider = new HashMap<>();
    }

    //根据实现类存放其所有方法
    public void provideServiceInterface(Object service){

        //根据接口的实现类获取服务名
        // String serviceName = service.getClass().getName();

        //获取该实现类的所有方法
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for(Class clazz : interfaces){
            interfaceProvider.put(clazz.getName(), service);
        }
    }

    //获取实现类的所有方法
    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
