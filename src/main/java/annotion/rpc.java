package annotion;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface rpc {

    /*
    *     远程主机地址,客户端接口必填
    *                  服务端可省略
     */
    String host() default "none";

    /*
    *     接口类型:服务端填"S"
    *              客户端填"C"
     */
    String Type();

    /*
    *     实体类名:服务端必填
    *             客户端可省略
     */
    String EntClassName() default "none";

    /**
     * 客户端指定连接服务端的RPC监听端口,服务端可省略
     */
    int port() default 29999;
}
