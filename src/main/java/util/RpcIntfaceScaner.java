package util;

import core.DRPC_Main;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import annotion.rpc;

import static core.DRPC_Main.*;
import static util.ClassUtils.*;


/**
 * 注解扫描器
 */
public class RpcIntfaceScaner {
    static String s_str = "S";
    static String c_str = "C";

    /**
     * 通过启动类配置需要扫描的包来填充这个字段
     */
    public static String[] packNames = new String[]{"???"};

    /**
     * 把扫描结结果保存到集合
     * @throws IOException
     */
    public static void fillData() throws IOException {
        for(String packName : packNames) {
            Iterator<Class<?>> it = ClassUtils.getClasses(packName).iterator();
            while (it.hasNext()) {
                Class<?> cls = it.next();
               // System.out.println(cls.getName());
                if (hasAnno(cls, rpc.class) && !cls.isAnnotation()) {
                    rpc a = cls.getAnnotation(rpc.class);
                    if (a.Type().equals(s_str)) {   //是服务端
                        serverInfo.put(getIntfaceName(cls.getName()), a.EntClassName());
                    } else {//是客户端
                        clientInfo.put(cls.getName(), new HashSet<>(Arrays.asList(cls.getDeclaredMethods())));
                        clientCllaIpInfo.put(cls.getName(), a.host());
                        clientCllaIpInfo1.put(cls.getName(),a.port());
                    }
                }
            }
        }
    }

    private static Set getIntefaceMethods(Class<?> inteface){
        Method[] arr = inteface.getDeclaredMethods();
        Set<Method> methodSet = new HashSet<>();
        for(Method i:arr){
            methodSet.add(i);
        }
        return methodSet;
    }

    /**
     * 测试某个类/接口是否包含此注解
     * @param c 类
     * @param annotation 注解
     * @return
     */
    private static boolean hasAnno(Class<?> c,Class<?> annotation){
        if(c.getAnnotation(rpc.class)!=null) return true;
        return false;
    }


}
