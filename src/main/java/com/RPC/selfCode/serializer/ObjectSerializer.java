package com.RPC.selfCode.serializer;

import java.io.*;

/**
 * Java 原生序列化 （实现Serializer接口）
 */

public class ObjectSerializer implements Serializer {

    // 序列化：把 Java 对象序列化成字节数组
    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = null;
        // 字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            // 对象输出流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            bytes = baos.toByteArray();
            oos.close();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // 反序列化：把字节数组反序列化成消息
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object object = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        try{
            ObjectInputStream ois = new ObjectInputStream(bais);
            object = ois.readObject();
            ois.close();
            bais.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    // 1 代表原生序列化
    @Override
    public int getTypeNum() {
        return 1;
    }
}
