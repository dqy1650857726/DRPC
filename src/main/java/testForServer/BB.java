package testForServer;

import annotion.rpc;

@rpc(Type = "S",EntClassName = "testForServer.BBimpl")
public interface BB {
    String a(int a);
}
