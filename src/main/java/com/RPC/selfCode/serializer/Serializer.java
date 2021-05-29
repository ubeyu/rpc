package com.RPC.selfCode.serializer;

/**
 * 序列化器的接口
 */

public interface Serializer {
    // 序列化：把 Java 对象序列化成字节数组
    byte[] serialize(Object object);

    // 反序列化：把字节数组反序列化成消息
    // 1.使用java自带的序列化方式，不用messageType也能得到相应的对象，因为自带的序列化字节数组里包含类信息
    // 2.其他方式需要指定消息格式，再根据messageType转化成相应对象
    Object deserialize(byte[] bytes, int messageType);

    //返回当前使用的序列器
    // 1.java自带序列化器
    // 2.json序列化器
    int getTypeNum();

    //根据类型号返回相应的序列化器，可扩展
    static Serializer getSerializerByTypeNum(int typeNum){
        switch (typeNum){
            case 1: return new ObjectSerializer();
            case 2: return new JsonSerializer();
            default: return null;
        }
    }
}
