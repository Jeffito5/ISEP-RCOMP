package tcp_cli_srv;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class TcpSrv {
    static ServerSocket sock;
    static Socket socket;
    private static HashMap<Socket, DataOutputStream> clientList=new HashMap<>();

    public static void main(String args[]) throws Exception {
        Socket cliSock;
        DataInputStream sIn = new DataInputStream(socket.getInputStream());
        byte[] info=new byte[255];

        sIn.read(info);
        try {
            sock = new ServerSocket(32507);
        } catch (IOException ex) {
            System.out.println("Failed to open server socket");
            System.exit(1);
        }

    }
}
