package core;

import nio.Server;
import test.BB;
import util.ProxyFactory;
import util.RpcIntfaceScaner;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.lang.Thread.*;
import static nio.Server.*;

/**
 *
 * 测试
 */
public class DRPC_Main {

    public static Map<String, String> serverInfo = new HashMap<>();
    public static Map<String, Set<Method>> clientInfo = new HashMap<>();
    public static Map<String,String> clientCllaIpInfo = new HashMap<>();
    public static Map<String,Integer> clientCllaIpInfo1 = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
//      new Runla(new String[]{"test","testForServer"},30001);
//
//      Thread.sleep(1000);
//
//        //注解扫描，扫描所有带有指定注解的类,分成客户端，服务端存入集合
//        RpcIntfaceScaner.fillData();
//        System.out.println(serverInfo);
//        System.out.println(clientInfo);
//        System.out.println(clientCllaIpInfo);;
        //为客户端生成代理，

//      BB o = (BB) ProxyFactory.get(BB.class);
//      String  cc = o.a(1);
//      System.out.println(o.a(2));
//      System.out.println(cc);
    }

    public static void logo(){
        LOGGER.info(" |////////  |`````.  |`````|  |`````");
        LOGGER.info(" |      /   |.....|  |.....|  |");
        LOGGER.info(" |     /    |   \\    |        |");
        LOGGER.info(" |/////     |    \\   |        |......  v1.0.0");
    }
}
