package com.RPC.register;

import java.net.InetSocketAddress;

/**
 * 把ServiceRegister抽象成接口，以后的注册中心实现这个接口即可
 * 注册中心两大基本功能：
 *  1.注册：保存服务与地址
 *  2.查询：根据服务名查找地址
 */


public interface ServiceRegister {
    // 根据服务名和服务地址，注册到注册中心
    void registerService(String serviceName, InetSocketAddress serverAddress);

    // 根据服务名，获取到服务地址
    InetSocketAddress getServiceAddressByServiceName(String serviceName);
}
