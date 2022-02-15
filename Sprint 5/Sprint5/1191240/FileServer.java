package tcp_files;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class FileServer extends Thread {

    private ServerSocket ss;
    private static String folder;

    public FileServer(int port) {
        try {
            ss = new ServerSocket(port);
            System.out.println("Server running on port " + port + "...");
        } catch (IOException e) {
            System.out.println("Unable to open designated port");
        }
    }

    public void run() {
        while (true) {
            try {
                Socket clientSock = ss.accept();
                saveFile(clientSock, folder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile(Socket clientSock, String folder) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        Date date = new Date();

        String filename = folder + "\\" + System.currentTimeMillis();
        System.out.println(filename);

        FileOutputStream fos = new FileOutputStream(filename);

        byte[] buffer = new byte[4096];
        //dis.
        int filesize = 15123; // Send file size in separate msg
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }

        fos.close();
        dis.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //set server port
        System.out.println("Enter the port number on which the server must run:");
        int portnum = scanner.nextInt();

        //set server folder
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        System.out.println("Choose the folder to receive the transfers:");

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            folder = chooser.getCurrentDirectory().getAbsolutePath();
            System.out.println("Selected Folder:" +  folder);
        }

        FileServer fs = new FileServer(portnum);
        fs.start();
    }

}