package com.RPC.server;

import java.io.IOException;

/**
 * 把RPCServer抽象成接口，以后的服务端实现这个接口即可
 */


public interface RPCServer {
    void start(int port) throws IOException;
    void stop();
}
