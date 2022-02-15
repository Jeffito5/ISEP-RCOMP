package tcp_files;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;

public class FileClient {

    private Socket s;

    public FileClient(String host, int port, String file) {
        try {
            s = new Socket(host, port);
            sendFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String file) throws IOException {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];

        while (fis.read(buffer) > 0) {
            dos.write(buffer);
        }

        fis.close();
        dos.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        System.out.println("Enter server's IP address:");
        String IPADD = scanner.next();
        System.out.println("Enter port number where the service is running:");
        int portnum = scanner.nextInt();
        System.out.println("Choose the file to transfer:");

        String filepath = new String("\\\\mafalda\\Home\\Files\\texto.txt");
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            filepath = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println("Selected file: " + filepath);
        }

        FileClient fc = new FileClient(IPADD, portnum , filepath);
    }

}