package core;

import util.ClassUtils;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import static util.ClassUtils.*;
import static core.DRPC_Main.*;

public class RemotCallTask{
    private String methodName;
    private Object[] args;
    private String className;

    public byte[] bytes;

    public RemotCallTask(String methodName,Object[] args,String className){
        this.methodName = methodName;
        this.args = args;
        this.className = className;
    }

    /**
     * @return 返回本地调用的结果
     * @throws Exception
     */

    public Object call() throws Exception {
        System.out.println("******" + getIntfaceName(className));
        className = serverInfo.get(getIntfaceName(className));
        System.out.println(className+"***************");
      //  System.out.println(className);
        Class aClass = Class.forName(className);
        Object obj = aClass.newInstance();
        //根据className,methodName找服务端对应的方法
        Method m;
        if((m=findMethod(aClass))!=null){
            return m.invoke(obj,args);
        }
        return null;
    }

    private Method findMethod(Class cls){
        for (Method i:cls.getDeclaredMethods()){
            if(i.getName().equals(methodName)) return i;
        }
        return null;
    }
}
