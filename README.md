# README
### QuikStart：
新建2个java工程(客户端，服务端)，导入jar包。

---

## 服务端:

服务端新建2个package(servers,serversimpl),端编写1个接口(Test)以及该接口的实现类(TestImpl):

接口:
```java
package servers;
import annotion.rpc;
@rpc( Type = "S",EntClassName = "serversimpl.TestImpl")
public interface Test {
    String get(int a,String b,float c);
}
```

实现类:
```java
package serversimpl;
public class TestImpl implements Test {
    @Override
    public String get(int a,String b,float c); {
        return b + a +c;
    }
}
```
新建main函数:
```java
public class startServerMain {
    public static void main(String[] args) throws Exception{
        /**配置
        *第一个参数为扫描带有@rpc注解的包
        *第二个参数为rpc服务监听的端口号
        **/
        Runla b = new Runla(new String[]{"Server"},23333);
    }
}
```

## 客户端
新建包，在包下编写一个接口Test
```java
package servers;
import annotion.rpc;
/**客户端和服务端是相对而言的
*@host 服务端ip地址,
*@type S表示这是服务端接口,C表示这是客户端接口
*@port 服务端的rpc监听端口
注意这里客户端接口要和服务端接口民一样，包名可以不一样，接口名要一样
**/
@rpc(host = "192.168.1.100", Type = "C",port=23333)
public interface Test {
    int get(String ip, int port);
}
```
测试调用
```java
public class StartClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        /*配置类
        *注意:在分布式调用中客户端和服务端可以是相对的
        *一个java工程既可以是客户端也可以是服务端。
        * 如果你确定这个工程不会作为服务端，那么在这里忽略第二个参数
        */
        new Runla(new String[]{"servers"},60000);

        //调用
        Test test = (Test)ProxyFactory.get(Test.class);
        String result = b.get(33,"你好",1.1f);
        System.out.println(result);
    }
}
```
结束。。

## Command：

当然这个小框架也可以在springboot里面使用，只需配置Runla启动类就行，一样的。

别再问怎么用了，再问自杀。


```
 |////////  |`````.  |`````|  |`````
 |      /   |.....|  |.....|  |
 |     /    |   \    |        |
 |/////     |    \   |        |......
 ```





