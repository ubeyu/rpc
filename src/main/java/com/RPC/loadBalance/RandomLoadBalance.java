package com.RPC.loadBalance;

import java.util.List;
import java.util.Random;

/**
 * 方式1：随机负载均衡
 */

public class RandomLoadBalance implements LoadBalance{

    @Override
    public String choiceOfLB(List<String> serverAddressList) {
        // 选择一个 [0,n) 内的随机值
        Random random = new Random();
        int chooseIndex = random.nextInt(serverAddressList.size());
        System.out.println("随机负载均衡：选择了 " + chooseIndex + " 号服务器");
        return serverAddressList.get(chooseIndex);
    }
}
