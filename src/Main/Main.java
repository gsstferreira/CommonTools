package Main;

import Base64Tools.Base64Tool;
import HttpTools.*;
import HttpTools.ServerResponse;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {

        String dir = "C:/Users/gusta/Downloads/earth.bmp";
        String dir2 = "C:/Users/gusta/Downloads/earth2.bmp";

        String test = "Hello World, Foo Bar in Katz Korner - Meow!";

        String b64 = Base64Tool.encodeString(test,true);

        String returnString = new String(Base64Tool.decodeString(b64,true));

        System.out.println(returnString);

        HttpServer server = new HttpServer(8080, new ServerAction() {
            @Override
            public AuthResult authorize(Socket socket, String path, List<Header> headers, String method) {
                return AuthResult.AUTHORIZED;
            }

            @Override
            public ServerResponse respondGET(String path, List<Header> headers) {
                switch (path) {
                    case "/foo":
                        return new ServerResponse(StatusCode.OK,null,"bar");
                    case "/marco":
                        return new ServerResponse(StatusCode.OK,null,"polo");
                    case "/random":
                        return new ServerResponse(StatusCode.OK,null,Integer.toString(new Random().nextInt()));
                    default:
                        return new ServerResponse(StatusCode.NOT_FOUND);
                }
            }

            @Override
            public ServerResponse respondPOST(String path, List<Header> headers, String data) {
                return new ServerResponse(StatusCode.NOT_FOUND);
            }

            @Override
            public ServerResponse respondPUT(String path, List<Header> headers, String data) {
                return new ServerResponse(StatusCode.NOT_FOUND);
            }

            @Override
            public ServerResponse respondDELETE(String path, List<Header> headers) {
                return new ServerResponse(StatusCode.NOT_FOUND);
            }

            @Override
            public ServerResponse respondCustom(String path, List<Header> headers, String method, String data) {
                return new ServerResponse(StatusCode.NOT_FOUND);
            }
        });

        server.start();
    }

    private static List<Header> stdHeaders() {
        List<Header> errList = new ArrayList<>();
        errList.add(new Header("Date",new Date().toString()));
        errList.add(new Header("Content-Type","text/plain"));

        return errList;
    }

}
