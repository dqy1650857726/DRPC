package core;

import nio.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RpcIntfaceScaner;

import java.io.IOException;

import static core.DRPC_Main.*;

/**
 * 启动类
 */
public class Runla {
    static final Logger LOGGER = LoggerFactory.getLogger(Runla.class);

    /**
     * @param pn 要扫描的包 扫描指定包下面的带@rpc注解的接口
     * @throws IOException  芜湖
     * @throws InterruptedException 芜
     */
    public Runla(String[] pn,int port) throws IOException, InterruptedException {
        logo();
        new Thread(()->{
            Server server = null;
            try {
                Server.port01 = port;
                server = Server.getServer();
                server.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        RpcIntfaceScaner.packNames = pn;
        Thread.sleep(100);

        //注解扫描，扫描所有带有指定注解的类,分成客户端，服务端存入集合
        RpcIntfaceScaner.fillData();
        LOGGER.debug(serverInfo.toString());
        LOGGER.debug(clientInfo.toString());
        LOGGER.debug(clientCllaIpInfo.toString());
    }
}
