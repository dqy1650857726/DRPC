package nio;

import core.RemotCallTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.util.Length;
import util.SerializableUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.FutureTask;

/**
 * rpc服务监听类
 */
public class Server {
    public static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    public static int port01;
    private ServerSocketChannel serverSocketChannel;
    Selector selector;
    SelectionKey selectionKey;
    public static volatile Server server = null;
    public static Server getServer() throws IOException {
        if(server == null)
            synchronized (Server.class){
                if(server==null)
                    server = new Server();
            }
        return server;
    }

    private Server() throws IOException {
        int port = port01;
        for(;;){
            if(initSocket(port)) break;
            ++port;
        }
        selector = Selector.open();
        selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
    }

    private boolean initSocket(int p) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(p));
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("端口被占用,正在重新选择端口，");
            return false;
        }
        LOGGER.info(("RPC监听端口:"+p));
        return true;
    }

    private void read(SelectionKey key) throws Exception {
        SocketChannel st = (SocketChannel) key.channel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
        int len = st.read(buf);
        if(len<=0) return;
//        System.out.println(len);
        buf.flip();
        Map<String,Object> info = (Map<String, Object>) SerializableUtil.toObject( SerializableUtil.catBt(len, buf.array()));
        LOGGER.debug("服务端取得入参");
        //准备执行本地方法
        RemotCallTask remotCallTask = new RemotCallTask((String)info.get("methodName"),(Object[]) info.get("args"),(String)info.get("className"));
        sendRes(st,remotCallTask.call());
        LOGGER.info("服务端完成调用！已返回结果");
        st.close();
    }

    private void sendRes(SocketChannel scl,Object o) throws IOException {
        byte[] bytes = SerializableUtil.toBytes(o);
        ByteBuffer buf = ByteBuffer.allocate(bytes.length);
        buf.put(bytes);
        buf.flip();
        scl.write(buf);
    }

    private void write(SelectionKey key) throws IOException {

    }

    private void accp(SelectionKey key) throws IOException {

        SocketChannel sc = serverSocketChannel.accept();
        sc.configureBlocking(false);
        sc.register(selector,SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        LOGGER.debug("新的连接到来");
    }

    public void run() throws Exception {
        LOGGER.debug("服务端运行");
        while (true){
            if(selector.select()>0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()){
                    SelectionKey key = it.next();
                    if(key.isAcceptable())//是连接事件
                        accp(key);
                    else if(key.isReadable())
                        read(key);
                    else if(key.isWritable())
                        write(key);
                    it.remove();
                }
            }

        }
    }
}
