package com.RPC.loadBalance;

import java.util.List;

/**
 * 方式2：轮询负载均衡
 */

public class RoundLoadBalance implements LoadBalance{
    private int chooseIndex = -1;
    @Override
    public String choiceOfLB(List<String> serverAddressList) {
        //每次加一
        chooseIndex++;
        chooseIndex = chooseIndex % serverAddressList.size();
        System.out.println("轮询负载均衡：选择了 " + chooseIndex + " 号服务器");
        return serverAddressList.get(chooseIndex);
    }
}
