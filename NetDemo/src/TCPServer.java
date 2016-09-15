import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by luca on 2016/9/15.
 */
public class TCPServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        Socket s = ss.accept();
        System.out.println("IP:" + s.getInetAddress().getHostAddress() + ",连接成功！");
        PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        String line = null;
        while ((line = br.readLine()) != null) {

            System.out.println("Client say:" + line);
            if (line.equals("over")) break;
            pw.println(line.toUpperCase());
            //while ((line = br2.readLine()) != null) {pw.println(line);break;}
        }
        br2.close();
        br.close();
        pw.close();
        s.close();
        ss.close();
        System.out.println("结束连接");
    }

}
