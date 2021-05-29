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

    //对象类型，第五次更新加入，用于在反序列化中获取对象的类型
    private Class<?> objectType;


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

    public Class<?> getObjectType() {
        return objectType;
    }

    public void setObjectType(Class<?> objectType) {
        this.objectType = objectType;
    }

    public RPCResponse(int stateCode, String stateMessage, Object object, Class<?> objectType) {
        this.stateCode = stateCode;
        this.stateMessage = stateMessage;
        this.object = object;
        this.objectType = objectType;
    }

    public RPCResponse(int stateCode, String stateMessage) {
        this.stateCode = stateCode;
        this.stateMessage = stateMessage;
    }

    public static RPCResponse sucess(Object object){
        RPCResponse rpcResponse = new RPCResponse(200, "创建 RPCResponse 成功", object, object.getClass());
        return rpcResponse;
    }

    public static RPCResponse fail(){
        RPCResponse rpcResponse = new RPCResponse(500, "创建 RPCResponse 失败");
        return rpcResponse;
    }
}