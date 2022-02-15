package HTTPStuf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HttpServerAjaxVoting extends Thread {
    String baseFolder;
    Socket sock;
    DataInputStream inS;
    DataOutputStream outS;

    public HttpServerAjaxVoting(Socket s, String f) {
        baseFolder = f;
        sock = s;
    }

    public void run() {
        HttpServerAjaxVoting http = new HttpServerAjaxVoting();
        try {
            outS = new DataOutputStream(sock.getOutputStream());
            inS = new DataInputStream(sock.getInputStream());
        } catch (IOException ex) {
            System.out.println("Thread error on data streams creation");
        }
        try {
            HTTPmessage task = new HTTPmessage(inS);
            HTTPmessage response = new HTTPmessage();
            System.out.println(task.getURI());

            if (task.getMethod().equals("GET")) {

                if (task.getURI().equals("/tasks")) {
                    response.setContentFromString(
                            RRH.getTasksInHTML(), "text/html");
                    response.setResponseStatus("200 Ok");

                } else if (task.getURI().equals("/login") && task.getContent() != null) {
                    response.setContentFromString(
                            RRH.getUserInHTML(task.getContentAsString()), "text/html");
                    response.setResponseStatus("200 Ok");

                } else if (task.getURI().equals("/logout")) {
                    response.setContentFromString(
                            RRH.logOut(), "text/html");
                    response.setResponseStatus("200 Ok");
                } else {
                    String fullname = baseFolder + "/";
                    if (task.getURI().equals("/")) {
                        fullname = fullname + "index.html";
                    } else {
                        fullname = fullname + task.getURI();
                    }
                    if (response.setContentFromFile(fullname)) {
                        response.setResponseStatus("200 Ok");
                    } else {
                        response.setContentFromString(
                                "<html><body><h1>404 File not found</h1></body></html>",
                                "text/html");
                        response.setResponseStatus("404 Not Found");
                    }
                }
                response.send(outS);
            }
        } catch (IOException ex) {
            System.out.println("Thread error when reading tasks");
        }
        try {
            sock.close();
        } catch (IOException ex) {
            System.out.println("CLOSE IOException");
        }
    }
}
