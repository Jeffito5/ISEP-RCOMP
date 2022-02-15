package cliente_server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * @author Luís Araújo
 */
public class CentroDistribuicao {
    static InetAddress serverIP;
    static Socket sock;
    static String[] ips = {"127.0.0.1"};
    static int index = 0;
    static Path path;
    static DataInputStream sIn;

    public static void main(String args[]) throws Exception {
        try {
            serverIP = InetAddress.getByName(ips[index]);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid server specified: " + args[0]);
            System.exit(1);
        }

        try {
            sock = new Socket(serverIP, 32507);
            System.out.println(sock.getPort());
        } catch (IOException ex) {
            System.out.println("Failed to establish TCP connection");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream sOut = new DataOutputStream(sock.getOutputStream());
        sIn = new DataInputStream(sock.getInputStream());

        sOut.write(2);
        byte answer = sIn.readByte();
        System.out.println("Lido");
        switch (answer) {
            case 0:
                //sOut.write(2);
                if (sIn.read() == 2) {
                    sendFile();
                    System.out.println("Success");
                } else {
                    System.out.println("Error");
                }
                break;
            default:
                break;
        }
    }

    public static void sendFile() throws IOException {
        String frase;
        int nChars;
        byte[] data = new byte[400];

        try {
            while (true) {
                nChars = sIn.read();
                if (nChars != 0) {
                    sIn.read(data, 0, nChars);
                    frase = new String(data, 0, nChars);
                    System.out.println(frase);
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Error");
        }

        DataOutputStream sOut = new DataOutputStream(sock.getOutputStream());
        Scanner in = new Scanner(System.in);
        String path = in.next();

        byte[] data1 = new byte[400];
        data1 = path.getBytes();
        sOut.write((byte) path.length());
        sOut.write(data1, 0, (byte) path.length());

        System.out.println("Insert the path");
        String path_2 = in.next();
        File file = new File(path_2);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        //Get socket's output stream
        OutputStream os = sock.getOutputStream();
        //Read File Contents into contents array
        byte[] contents;
        long fileLength = file.length();
        long current = 0;
        long start = System.nanoTime();
        while (current != fileLength) {
            int size = 10000;
            if (fileLength - current >= size)
                current += size;
            else {
                size = (int) (fileLength - current);
                current = fileLength;
            }
            contents = new byte[size];
            bis.read(contents, 0, size);
            os.write(contents);
            System.out.println("Sending file ... " + (current * 100) / fileLength + "% complete!");
        }
        os.flush();
        //File transfer done. Close the socket connection!
        sock.close();
        System.out.println("File sent successfully!");
    }
}

