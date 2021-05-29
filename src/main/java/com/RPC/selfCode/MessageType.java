package com.RPC.selfCode;

/**
 * 用于判断当前读到的是请求还是响应，从而返回不同代号
 */

public enum MessageType {
    REQUEST(1),RESPONSE(2);

    private int code;
    public int getCode(){
        return code;
    }

    MessageType(int code) {
        this.code = code;
    }
}
