package test;

import annotion.rpc;

@rpc(host = "127.0.0.1",Type = "C",port = 30001)
public interface BB {
    String a(int a);
}
