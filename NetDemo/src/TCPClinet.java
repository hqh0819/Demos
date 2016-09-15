import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by luca on 2016/9/15.
 */
public class TCPClinet {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1", 8888);
        System.out.println("连接到：127.0.0.1成功");
        PrintStream pw = new PrintStream(s.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br2.readLine()) != null) {

            pw.println(line);
            if (line.equals("over")) break;
            if ((line = br.readLine()) != null) System.out.println("Server say:" + line);


        }
        br.close();
        br2.close();
        pw.close();
        s.close();
        System.out.println("结束连接");

    }
}
