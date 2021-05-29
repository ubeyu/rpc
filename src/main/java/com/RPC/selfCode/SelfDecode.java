package com.RPC.selfCode;

import com.RPC.selfCode.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class SelfDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // ByteBuf byteBuf 表示输入流
        // List<Object> list 存放解码后的对象

        // 1. 读取传入的消息类型数字号
        int messageType = byteBuf.readShort();

        // 2. 判断若不属于已有的消息类型，则输出错误
        if(messageType != MessageType.REQUEST.getCode() && messageType != MessageType.RESPONSE.getCode()){
            System.out.println("解码器：暂不支持此种消息类型！！！");
            return;
        }

        // 3. 读取传入的序列器类型数字号
        int serializerTypeNum = byteBuf.readShort();

        // 4. 根据数字号获取序列器
        Serializer serializer = Serializer.getSerializerByTypeNum(serializerTypeNum);

        // 5. 序列器若不存在，抛出异常
        if(serializer == null) {
            throw new RuntimeException("解码器：暂无此类型的序列器！！！");
        }

        // 6.获取字节数组长度
        int length = byteBuf.readShort();

        // 7.读入字节数组
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        // 8.使用对应的解码器，对字节数组按消息类型进行解码，添加到对象 List 中
        Object deserialize = serializer.deserialize(bytes, messageType);
        list.add(deserialize);
    }
}
