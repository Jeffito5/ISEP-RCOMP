package tcp_cli_srv;

import tcp_files.FileClient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class TcpCli {
    static InetAddress serverIP;
    static Socket sock;
    static FileClient fileClient;

    public static void main(String args[]) throws Exception {
        //PROTOCOLO TCP
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
        //END REGION- PROTOCOLO TCP

        //UTILS
        Scanner in = new Scanner(System.in);
        DataOutputStream sOut = new DataOutputStream(sock.getOutputStream());

        //VARIÁVEIS
        // Versão -> 0 // Código -> [0;255] // Número de bytes -> [0;255] // Data -> [0;255]
        int version, code, number_of_bytes, data, auxVersion = 0, auxCode = 0, auxNumBytes = 0, auxData = 0;
        byte version2, code2, number_of_bytes2, data2;

        //LÊ VARIÁVEIS INTEIRAS
        version = in.nextInt();
        code = in.nextInt();
        number_of_bytes = in.nextInt();
        data = in.nextInt();

        //CONVERTE AS VARIÁVEIS INTEIRAS EM BYTES
        version2 = (byte) version;
        code2 = (byte) code;
        number_of_bytes2 = (byte) number_of_bytes;
        data2 = (byte) data;

        //TRANSFORMA OS BYTES EM INTEIROS E VERIFICA SE ESTÃO ENTRE 0 E 255
        if (version2 < 0) {
            auxVersion = version2 + 256;
        }
        if (code2 < 0) {
            auxCode = code2 + 256;
        }
        if (number_of_bytes2 < 0) {
            auxNumBytes = number_of_bytes2 + 256;
        }
        if (data2 < 0) {
            auxData = data2 + 256;
        }

        //CASO OS INTEIROS TRANSFORMADOS ESTEJAM FORA DOS LIMITES ENTÃO AVISA O CLIENTE
        if (auxVersion < 0 || auxVersion > 255 || auxCode < 0 || auxCode > 255 || auxNumBytes < 0 || auxNumBytes > 255 || auxData < 0 || auxData > 255) {
            System.out.println("Argument out of bounds.\n");
        }

        //ADICIONA AS VARIÁVEIS BYTES AO ARRAY DE BYTES
        byte[] info = new byte[400];
        info[0] = version2;
        info[1] = code2;
        info[2] = number_of_bytes2;
        info[3] = data2;

        //ENVIA O ARRAY PARA O SERVER
        sOut.write(info);
    }
}
