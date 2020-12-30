package cn.net.mayh.BinaryTree;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoClientApp {
    public static void main(String[] args) throws IOException, InterruptedException {

        final int listenPort = 12345;

        // 启动客户端
        EchoClient client = new EchoClient(listenPort);
        client.startService();

    }
}
class EchoClient  extends Server{

    public EchoClient(int listenPort) {
        super(listenPort);
    }

    @Override
    public void startService() throws IOException {
        Socket client = new Socket("localhost",listenPort);
        InputStream inputStream = client.getInputStream();
        OutputStream outputStream = client.getOutputStream();
        byte[] bytes = new byte[1024];
        int length  = inputStream.read(bytes);
        System.out.println(new String(bytes, 0, length - 1));
        Scanner sc = new Scanner(System.in);

        if (sc.hasNext()) {
            String line = sc.nextLine();
            outputStream.write(line.getBytes());

            StringBuilder sb = new StringBuilder();
            int readNum = 0;
            while (line.length() < readNum + 61){
                length = inputStream.read(bytes);
                readNum += length;
                String str = new String(bytes, 0, length - 1);
                sb.append(str);
            }
            System.out.println(sb);
        }



        inputStream.close();
        client.close();
    }
}