package tcp_cli_srv;

import tcp_files.FileClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class TcpCli {
    static InetAddress serverIP;
    static Socket sock;
    static FileClient fileClient;

    public static void main(String args[]) throws Exception {
        if (args.length != 1) {
            System.out.println("Server IPv4/IPv6 address or DNS name is required as argument");
            System.exit(1);
        }

        try {
            serverIP = InetAddress.getByName(args[0]);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid server specified: " + args[0]);
            System.exit(1);
        }

        try {
            sock = new Socket(serverIP, 32507);
        } catch (IOException ex) {
            System.out.println("Failed to establish TCP connection");
            System.exit(1);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream sOut = new DataOutputStream(sock.getOutputStream());
        //File ficheiro = new File("file.txt");
        //ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ficheiro));

        // Versão -> 0 // Código -> [0;255] // Número de bytes -> [0;255] //
        byte version, code, number_of_bytes, data;
        version = (byte) in.read();
        code = (byte) in.read();
        number_of_bytes = (byte) in.read();
        data = (byte) in.read();
        byte[] info = new byte[]{version, code, number_of_bytes, data};
        sOut.write(info);
        if (version < 0 || version > 127 || number_of_bytes < 0 || number_of_bytes > 127 || data < 0 || data > 127) {
            System.out.println("Argument out of bounds.\n");
        }
        if (code > 0 && code < 127) {
            switch (code) {
                case 0://- Pedido de teste sem qualquer efeito para além da devolução de uma resposta com código 2. Este pedido
                    //não transporta dados.
                    break;
                case 1:
                    sock.close();
                    break;
                case 2://Resposta vazia (não transporta dados) que acusa a receção de um pedido. É enviada em resposta a
                    //pedidos com código 0 e código 1, mas poderá ser usada em outros contextos.
                    break;
                case 3:
                    String path;
                    path = in.readLine();
                    fileClient.sendFile(path);
                    break;
                default:
                    break;
            }
        }


    }
}

//        version = in.read();
//        try {
//            out.writeObject(version); //grava a lista
//        } finally {
//            out.close(); // fecha o ficheiro
//        }
//        sOut.writeChars(String.valueOf(ficheiro));
//
//        code = in.read();
//        try {
//            out.writeObject(code); //grava a lista
//        } finally {
//            out.close(); // fecha o ficheiro
//        }
//        sOut.writeChars(String.valueOf(ficheiro));
//
//        number_of_bytes = in.read();
//        try {
//            out.writeObject(number_of_bytes); //grava a lista
//        } finally {
//            out.close(); // fecha o ficheiro
//        }
//        sOut.writeChars(String.valueOf(ficheiro));
//
//        data = in.read();
//        try {
//            out.writeObject(data); //grava a lista
//        } finally {
//            out.close(); // fecha o ficheiro
//        }
//        sOut.writeChars(String.valueOf(ficheiro));
