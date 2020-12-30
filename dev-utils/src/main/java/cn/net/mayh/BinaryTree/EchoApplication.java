package cn.net.mayh.BinaryTree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class EchoApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        final int listenPort = 12345;

        // 启动服务端
        EchoServer server = new EchoServer(listenPort);
        server.startService();


    }

}
abstract class Server{
    protected int listenPort ;
    public Server(int listenPort){
        this.listenPort = listenPort;
    }
    public abstract void startService() throws IOException;
}
class EchoServer extends Server{
    public EchoServer(int listenPort) {
        super(listenPort);
    }

    @Override
    public void startService() throws IOException {
        ServerSocket socket = new ServerSocket( listenPort);
        while (true){
            Socket client = socket.accept();
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();
            byte[] bytes = new byte[1024];
            int length = -1;
            byte[] pre = (new Date().toString() + ":").getBytes();
            outputStream.write(pre);
            outputStream.write("Welcome to My Echo Server.".getBytes());
            while (true){
                length = inputStream.read(bytes);
                String str = new String(bytes, 0, length - 1);
                if ("bye".equals(str)) {
                    break;
                }
                outputStream.write(pre);
                outputStream.write(bytes,0,length-1);
            }
            inputStream.close();
            client.close();
        }
    }
}
