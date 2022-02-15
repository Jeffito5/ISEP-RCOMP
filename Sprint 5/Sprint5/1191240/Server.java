package 1191240;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Thread {
	
	private final static PORT=32507; 
	private ServerSocket sock;
	static Socket socket;
	private static HashMap<Socket,DataOutputStream> alojadores= new HashMap<>();
	
	public Server(int numPort){
		try {
			sock=new Socket(numPort);
			System.out.println("Server correndo no port " + port + "...");
		}catch(IOException e){
			 System.out.println("Unable to open designated port");
		}
	}
	
	public static void main(String args[]) throws Exception {
        Scanner in = new Scanner(System.in);
        Socket cliSock;
        DataInputStream sIn;
        DataOutputStream sOut;
        byte[] info = new byte[400];
        try {
            sIn = new DataInputStream(socket.getInputStream());
            sOut = new DataOutputStream(socket.getOutputStream());
            while (true) {
                int messageLength = sIn.readInt();
                System.out.println(messageLength);
                if (messageLength > 0) {
                    sIn.read(info, 0, messageLength);
                    switch (info[1]) {
                        case 0://- Pedido de teste sem qualquer efeito para além da devolução de uma resposta com código 2. Este pedido
                            //não transporta dados.
                            info[0]=0;
                            info[1]=2;
                            info[2]=0;
                            info[3]=0;
                            sOut.write(info);
                            break;
                        case 1:
                            sOut.writeInt(info.length);
                            //We need to be sure that all the writes are completed before we close the stream
                            sOut.flush();
                            sOut.write(info);
                            sOut.flush();
                            break;
                        case 2://Resposta vazia (não transporta dados) que acusa a receção de um pedido. É enviada em resposta a
                            //pedidos com código 0 e código 1, mas poderá ser usada em outros contextos.
                            info[0]=0;
                            info[1]=2;
                            info[2]=0;
                            info[3]=0;
                            sOut.write(info);
                            break;
                        case 3:
                            System.out.println("Digite o caminho do ficheiro a enviar");
                            String path;
                            path = in.next();
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println("IOException");
        } catch (Exception e) {
//            e.printStackTrace();
        }


        System.out.println("New client connection from " + clientList.toString() +
                ", port number " + sock.getLocalPort());
        try {
            sock = new ServerSocket(32507);
        } catch (IOException ex) {
            System.out.println("Failed to open server socket");
            System.exit(1);
        }

    }
}

