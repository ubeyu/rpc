package com.RPC.selfCode;

import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;
import com.RPC.selfCode.serializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 按照自定义的消息格式依次写入，传入的数据可以是 rpcRequest 或 rpcResponse
 * 编码器需要持有一个 serialize 器，负责将 传入的对象 序列化 成字节数组
 */

public class SelfEncode extends MessageToByteEncoder {

    private Serializer serializer;

    public SelfEncode(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        // Object o 表示要编码的对象
        // ByteBuf byteBuf 表示输出流

        //打印出对象的类
        System.out.println("编码器：正在对 " + o.getClass() + " 类进行编码");

        // 判断继承关系
        // 1. 父类.class.isAssignableFrom(子类.class)
        // 2. 子类实例 instanceof 父类类型

        //判断消息类型，写入不同数字号
        if(o instanceof RPCRequest){
            // 请求为 1
            byteBuf.writeShort(MessageType.REQUEST.getCode());
        }else if(o instanceof RPCResponse){
            // 应答为 2
            byteBuf.writeShort((MessageType.RESPONSE.getCode()));
        }

        //写入序列化的方式代号
        byteBuf.writeShort(serializer.getTypeNum());

        //将对象序列化成字节数组
        byte[] serialize = serializer.serialize(o);

        //写入长度
        byteBuf.writeShort(serialize.length);

        //写入字节数组
        byteBuf.writeBytes(serialize);
    }
}
