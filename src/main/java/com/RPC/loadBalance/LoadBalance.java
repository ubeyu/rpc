package com.RPC.loadBalance;

import java.util.List;

/**
 * 把LoadBalance抽象成接口，各种方式的负载均衡实现此接口
 * 包含balance方法：
 * 1. 传入服务器地址列表 List<String>
 * 2. 根据不同的负载均衡策略，返回一个 String
 */

public interface LoadBalance {
    // 由负载均衡选择出一个地址String
    // 传入serverAddressList列表，返回一个String
    String choiceOfLB(List<String> serverAddressList);
}
