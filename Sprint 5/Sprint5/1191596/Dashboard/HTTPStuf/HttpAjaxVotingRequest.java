package HTTPStuf;

import java.net.Socket;

//The purpose of this class is handling HTTP requests from clients
public class HttpAjaxVotingRequest extends Thread {

    public void handlingRequestClient(Socket inS) {
        HTTPmessage request = new HTTPmessage(inS);
        HTTPmessage response = new HTTPmessage();

        if (request.getMethod().equals("GET")) {
            if (request.getURI().equals("/votes")) {
                response.setContentFromString(
                        HttpServerAjaxVoting.getVotesStandingInHTML(), "text/html");
                response.setResponseStatus("200 Ok");
            } else {
                String fullname = baseFolder + "/";
                if (request.getURI().equals("/")) fullname = fullname + "index.html";
                else fullname = fullname + request.getURI();
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
        } else { // NOT GET
            if (request.getMethod().equals("PUT")
                    && request.getURI().startsWith("/votes/")) {
                HttpServerAjaxVoting.castVote(request.getURI().substring(7));
                response.setResponseStatus("200 Ok");
            } else {
                response.setContentFromString(
                        "<html><body><h1>ERROR: 405 Method Not Allowed</h1></body></html>",
                        "text/html");
            }
        }
    }
