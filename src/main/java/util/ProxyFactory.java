package util;

import core.DRPC_Main;
import nio.Client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static core.DRPC_Main.*;

/**
 * 代理工厂
 */
public class ProxyFactory {
    /**
     * 生成指定接口的代理对象
     * @param cls 接口的class对象
     * @return
     */
    public static Object get(Class cls){
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Map<String,Object> info = new HashMap<>();
                fillInfoMap(info,args,method,cls);
                Client c = new Client(clientCllaIpInfo.get(cls.getName()),clientCllaIpInfo1.get(cls.getName()));
                byte[] bcode = SerializableUtil.toBytes(info);
                c.sendParame(bcode);
                Object res = SerializableUtil.toObject( c.getRes());
                c.closeClient();
                return res;
            }

            private void fillInfoMap(Map info,Object[] args,Method m,Class<?> clazz){
                info.put("className",clazz.getName());
                info.put("args",args);
                info.put("methodName",m.getName());
            }
        });
    }

//    private static class ProxyMetot implements InvocationHandler{
//
//        @Override
//        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//            return null;
//        }
//    }
}
