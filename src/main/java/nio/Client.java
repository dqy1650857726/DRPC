package nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    String host;
    int port;
    SocketChannel socketChannel;
    boolean isConnect;
    public Client(String host,int port) throws IOException {
        this.host=host;
        this.port = port;
        isConnect = false;
        initAndConnect();
    }

    private void initAndConnect() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(host,port));
        isConnect = true;
        LOGGER.debug("客户端吧成功连接上服务端");
    }

    public void sendParame(byte[] bcode) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(bcode.length);
        buf.put(bcode);
        buf.flip();
        socketChannel.write(buf);
        LOGGER.debug("发送入参成功");
    }

    public byte[] getRes() throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(1024);
        socketChannel.read(buf);
        buf.flip();
        LOGGER.info("客户端RPC调用完成！");
        return buf.array();
    }

    public void closeClient() throws IOException {
        socketChannel.close();
    }

}
