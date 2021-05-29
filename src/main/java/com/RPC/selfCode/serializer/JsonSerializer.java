package com.RPC.selfCode.serializer;

import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Json 序列化 （实现Serializer接口）
 * 引入 fastjson 依赖
 */


public class JsonSerializer implements Serializer {
    // 序列化：把 Java 对象序列化成字节数组
    @Override
    public byte[] serialize(Object object) {
        // 利用 JSONObject 将对象直接转化为字节数组
        byte[] bytes = JSONObject.toJSONBytes(object);
        return bytes;
    }

    // 反序列化：把字节数组反序列化成消息
    // 对于json序列化的方式是通过把对象转化成字符串，丢失了Data对象的类信息，
    // 所以deserialize需了解对象的类信息，根据类信息把JsonObject -> 对应的对象
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object object = null;
        switch (messageType){
            // 请求反序列化
            case 1:
                // 利用 JSON 和 类信息 直接将字节数组转化为 RPCRequest 对象
                RPCRequest rpcRequest = JSON.parseObject(bytes, RPCRequest.class);

                // 若参数值为空，直接返回
                if(rpcRequest.getParams() == null)  return rpcRequest;

                // 所有根据参数数组定义 objects
                Object[] objects = new Object[rpcRequest.getParams().length];

                // 对于基本数据类型，fastjson可以读出；对于对象类型，fastjson需要进行转化
                for(int i=0; i<objects.length; i++){
                    Class<?> paramsType = rpcRequest.getParamsTypes()[i];
                    // 判断继承关系
                    // 1. 父类.class.isAssignableFrom(子类.class)
                    // 2. 子类实例 instanceof 父类类型
                    if(paramsType.isAssignableFrom(rpcRequest.getParams()[i].getClass())){
                        objects[i] = rpcRequest.getParams()[i];
                    }else{
                        //根据数据值和数据类型转化为 Java 对象
                        objects[i] = JSONObject.toJavaObject((JSONObject) rpcRequest.getParams()[i], rpcRequest.getParamsTypes()[i]);
                    }
                }
                rpcRequest.setParams(objects);
                object = rpcRequest;
                break;

            // 响应反序列化
            case 2:
                // 利用 JSON 和 类信息 直接将字节数组转化为 RPCResponse 对象
                RPCResponse rpcResponse = JSON.parseObject(bytes, RPCRequest.class);
                // 所有根据参数数组定义 objects
                Class<?> objectType = rpcResponse.getObjectType();

                // 判断继承关系，转化为 Java 对象
                if(! objectType.isAssignableFrom(rpcResponse.getObject().getClass())){
                    rpcResponse.setObject(JSONObject.toJavaObject((JSON) rpcResponse.getObject(), objectType));
                }
                object = rpcResponse;
                break;

            // 其他情况
            default:
                System.out.println("JSON序列器：暂不支持此种消息类型！！！");
                throw new RuntimeException();
        }
        return object;
    }

    // 2 代表原生序列化
    @Override
    public int getTypeNum() {
        return 2;
    }
}
