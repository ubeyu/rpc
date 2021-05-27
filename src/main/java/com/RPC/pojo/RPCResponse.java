package com.RPC.pojo;

import java.io.Serializable;

/**
 * 一个应用中我们不可能只传输一种类型的数据，由此我们将传输对象抽象成为Object
 * Rpc需要经过网络传输，有可能失败，类似于http，引入状态码和状态信息表示服务调用成功还是失败
 */

public class RPCResponse implements Serializable {
    //状态码和状态信息
    private int stateCode;
    private String stateMessage;
    //对象数据
    private Object object;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(String stateMessage) {
        this.stateMessage = stateMessage;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static RPCResponse sucess(Object object){
        RPCResponse rpcResponse = new RPCResponse();
        rpcResponse.setStateCode(200);
        rpcResponse.setStateMessage("成功");
        rpcResponse.setObject(object);
        return rpcResponse;
    }

    public static RPCResponse fail(){
        RPCResponse rpcResponse = new RPCResponse();
        rpcResponse.setStateCode(500);
        rpcResponse.setStateMessage("失败");
        return rpcResponse;
    }
}
