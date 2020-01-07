package HttpTools;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HttpServer {

    private final int serverPort;
    private final ServerAction performedAction;

    private boolean running;
    private ServerSocket serverSocket;

    /**
     Creates an instance of HttpServer, performing specified action and binding on specified port.
     Port binding is not performed on constructor.
     @param port Network port to listen to
     @param action Interface method to define how the server will process requests and send responses.
     @throws NullPointerException if action is null
     */
    public HttpServer(int port, ServerAction action) throws NullPointerException {
        this.serverPort = port;

        if(action == null) {
            throw new NullPointerException("Server action cannot be null.");
        }

        this.performedAction = action;
        this.running = false;
    }

    public int getServerPort() {
        return serverPort;
    }

    /**
    Start the server, binding to port.
    @throws IOException if network port is already bound, calling start() on an already running server (as port will be bound)
    */
    public void start() throws IOException {

        serverSocket = new ServerSocket(serverPort);
        running = true;

        new Thread(() -> {
            while (running){
                try {
                    Socket s = serverSocket.accept();

                    new Thread(() -> {
                        try {
                            readSocket(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
                catch (Exception e){
                    if(!serverSocket.isClosed()) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                serverSocket.close();
            }
            catch (Exception ignored) {}
        }).start();
    }

    /**
    Stops a running server. Socket will be closed, preventing new requests, but ongoing ones will have responses sent properly.
    @throws IOException if calling stop() on an already stopped/inactive server
    */
    public void stop() throws IOException {

        if(!running) {
            throw new IOException("Server already closed.");
        }
        else {
            running = false;
            serverSocket.close();
        }
    }

    private void readSocket(Socket s) throws IOException {

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

        String firstLine = inputReader.readLine();

        if(firstLine != null) {
            StringTokenizer tokenizer = new StringTokenizer(firstLine);

            String method = tokenizer.nextToken().toUpperCase();
            String action = tokenizer.nextToken();

            List<Header> headers = new ArrayList<>();
            StringBuilder sb = new StringBuilder();

            while(inputReader.ready()) {

                char c = (char) inputReader.read();

                if(c == '\n') {
                    String line = sb.toString();
                    sb.setLength(0);

                    if(line.isEmpty()) {
                        break;
                    }
                    else {
                        String[] headerSplit = line.split(":");
                        Header h = new Header(headerSplit[0],headerSplit[1].split(","));
                        headers.add(h);
                    }
                }
                else if(c != '\r') {
                    sb.append(c);
                }
            }

            while (inputReader.ready()) {
                sb.append((char) inputReader.read());
            }

            String data = sb.toString().trim();
            ServerResponse response;

            switch (performedAction.authorize(s,action,headers,method)) {
                case UNAUTHORIZED:
                    response = new ServerResponse(StatusCode.UNAUTHORIZED);
                    break;
                case FORBIDDEN:
                    response = new ServerResponse(StatusCode.FORBIDDEN);
                    break;
                case AUTHORIZED:
                    switch (method) {
                        case "GET":
                            response = performedAction.respondGET(action,headers);
                            break;
                        case "POST":
                            response = performedAction.respondPOST(action,headers,data);
                            break;
                        case "PUT":
                            response = performedAction.respondPUT(action,headers,data);
                            break;
                        case "DELETE":
                            response = performedAction.respondDELETE(action,headers);
                            break;
                        default:
                            response = performedAction.respondCustom(action,headers,method,data);
                            break;
                    }
                    break;
                default:
                    response = new ServerResponse(StatusCode.INTERNAL_SERVER_ERROR);
                    break;
            }

            outputWriter.write(String.format("HTTP/1.1 %s\n",response.getResponseCode()));

            for (Header h:response.getResponseHeaders()) {
                StringBuilder hb = new StringBuilder();
                for (String v:h.getValues()) {
                    hb.append(v).append(',');
                }
                hb.setLength(hb.length() - 1);
                outputWriter.write(String.format("%s: %s\n",h.getName(),hb.toString()));
            }
            if(response.getResponseBody() != null && response.getResponseBody().length() > 0) {
                byte[] dataBytes = response.getResponseBody().getBytes();
                outputWriter.write(String.format("Content-length: %d\n\n",dataBytes.length));
                outputWriter.write(response.getResponseBody() + '\n');
            }
            outputWriter.flush();

            try {
                s.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            s.close();
        }
    }
}
