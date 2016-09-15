import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by luca on 2016/9/15.
 */
public class MuiltTCPServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        Socket socket;
        while ((socket = ss.accept()) != null) {
            new Thread(new TCPSocket(socket)).start();
        }
    }
}

class TCPSocket implements Runnable {

    Socket socket;

    public TCPSocket(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream pw = new PrintStream(socket.getOutputStream(), true);

            String line;

            while ((line = br.readLine()) != null) {
                System.out.println("Client say:" + line);
                if (line.equals("over")) break;
                pw.println(line.toUpperCase());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}