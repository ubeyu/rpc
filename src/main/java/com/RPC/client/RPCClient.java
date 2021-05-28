package com.RPC.client;


import com.RPC.pojo.RPCRequest;
import com.RPC.pojo.RPCResponse;

public interface RPCClient {
    RPCResponse sendRPCRequest(RPCRequest rpcRequest);
}
