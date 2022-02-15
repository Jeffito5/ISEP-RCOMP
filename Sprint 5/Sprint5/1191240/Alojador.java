package 1191240;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.io.File;

public class Alojador{
	static final int PORT=32507;
	static Socket socket;
    static InetAddress alojadorIP;
	String path;
	
	public Alojador(String path, InetAddress serverIP){
		
		if(host==null){
			System.out.println("Server IPv4/IPv6 address or DNS name is required as argument");
            System.exit(1);
		}
		
		try {
			socket=new Socket(alojadorIP,PORT);
		} catch (IOException ex) {
            System.out.println("Failed to establish TCP connection");
            System.exit(1);
        }
	}
	
	public void receiveFile(String file_path){
		DataOutputStream dos = new DataOutputStream(path);
        FileInputStream fis = new FileInputStream(file_path);
		byte[] buffer = new byte[4096];

        while (fis.read(buffer) > 0) {
            dos.write(buffer);
        }

        fis.close();
        dos.close();
	}
	
	
	public static void main(String[] arg){
		if (args.length != 1) {
            System.out.println("Server IPv4/IPv6 address or DNS name is required as argument");
            System.exit(1);
        }
		
		Scanner scanner = new Scanner(System.in);
		
		try {
            serverIP = InetAddress.getByName(args[0]);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid server specified: " + args[0]);
            System.exit(1);
        }

		System.out.println("Digite o path do Alojador:");
		String file_path=scanner.next();
		
		Alojador al = new Alojador(file_path,alojadorIP);
				
	}
	
}