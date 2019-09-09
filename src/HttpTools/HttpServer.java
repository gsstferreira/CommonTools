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

    private ServerSocket serverSocket;
    private boolean running;

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

    public void stop() throws IOException {

        if(!running) {
            throw new IOException("Server already closed.");
        }
        else {
            running = false;
            serverSocket.close();
        }
    }

    public void queryStop() {
        running = false;
    }

    private void readSocket(Socket s) throws IOException {

        BufferedReader inputReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedWriter outputWriter = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

        String firstLine = inputReader.readLine();

        if(firstLine != null) {
            StringTokenizer tokenizer = new StringTokenizer(firstLine);

            String method = tokenizer.nextToken();
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

            ServerResponse response = performedAction.respond(s,action, headers, method, data);

            outputWriter.write(String.format("HTTP/1.1 %s\n",response.getResponseMethod()));

            for (Header h:response.getResponseHeaders()) {
                StringBuilder hb = new StringBuilder();
                for (String v:h.getValues()) {
                    hb.append(v).append(',');
                }
                hb.setLength(hb.length() - 1);
                outputWriter.write(String.format("%s: %s\n",h.getName(),hb.toString()));
            }
            if(response.getResponseData() != null && response.getResponseData().length() > 0) {
                byte[] dataBytes = response.getResponseData().getBytes();
                outputWriter.write(String.format("Content-length: %d\n\n",dataBytes.length));
                outputWriter.write(response.getResponseData() + '\n');
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
