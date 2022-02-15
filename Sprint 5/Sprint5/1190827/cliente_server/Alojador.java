package cliente_server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author Luís Araújo
 */

public class Alojador {
    static ServerSocket ss;
    static DataOutputStream sOut;
    static DataInputStream sIn;

    public static void main(String args[]) throws Exception {
        Socket cliSock;

        try {
            ss = new ServerSocket(32507);
        } catch (IOException ex) {
            System.out.println("Failed to open server socket");
            System.exit(1);
        }

        while (true) {
            cliSock = ss.accept();
            System.out.println("Uma pessoa adicionada");
            sOut = new DataOutputStream(cliSock.getOutputStream());
            sIn = new DataInputStream(cliSock.getInputStream());
            byte answer = sIn.readByte();
            System.out.println("Byte lido");

            switch (answer) {
                case 0://- Pedido de teste sem qualquer efeito para além da devolução de uma resposta com código 2. Este pedido
                    //não transporta dados.
                    sOut.write(2);
                    break;
                case 1:
                    sOut.write(2);
                    cliSock.close();
                    break;
                case 2://Resposta vazia (não transporta dados) que acusa a receção de um pedido. É enviada em resposta a
                    //pedidos com código 0 e código 1, mas poderá ser usada em outros contextos.
                    sOut.write(0);
                    new Thread(new ReceiveFiles(cliSock)).start();
                    sOut.write(2);
                    break;
                default:
                    break;
            }

        }
    }
}

class ReceiveFiles implements Runnable {
    static DataOutputStream sOut;
    static DataInputStream sIn;
    private Socket s;
    String path;

    public ReceiveFiles(Socket cli_s) {
        s = cli_s;
    }

    public void receiveFile(String file_path) throws IOException {
        byte[] contents = new byte[10000];
        //Initialize the FileOutputStream to the output file's full path.
        File file = new File(file_path);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        InputStream is = s.getInputStream();
        //No of bytes read in one read() call
        int bytesRead = 0;
        while((bytesRead=is.read(contents))!=-1) {
            bos.write(contents, 0, bytesRead);
            bos.flush();
        }
        fos.close();
        is.close();
        s.close();
        System.out.println("File saved successfully!");

    }

    public void run() {
        InetAddress clientIP;
        clientIP = s.getInetAddress();
        System.out.println("New client connection from " + clientIP.getHostAddress() +
                ", port number " + s.getPort());
        try {
            sOut = new DataOutputStream(s.getOutputStream());
            sIn = new DataInputStream(s.getInputStream());
            String st="Name of the new file + its extension";

            byte[] data = new byte[400];
            data = st.getBytes();
            sOut.write((byte)st.length());
            sOut.write(data,0,(byte)st.length());

            String path="";

            int nChars;
            byte[] data1 = new byte[400];

            try {
                while (true) {
                    nChars = sIn.read();
                    if (nChars != 0) {
                        sIn.read(data, 0, nChars);
                        path = new String(data, 0, nChars);
                        //System.out.println(path);
                        break;
                    }
                }
            }catch(IOException ex){
                System.out.println("Error");
            }

            String filepath = new String("C:\\Users\\User\\Documents\\rcomp-20-21-2dh-g05\\Sprint5\\1190827\\FilesSaved\\"+path);
            receiveFile(filepath);

            s.close();
        } catch (IOException ex) {
            System.out.println("IOException");
        }

    }
}


