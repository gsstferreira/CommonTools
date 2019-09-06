package Main;

import Base64Tools.Base64Tool;
import HttpTools.*;
import HttpTools.ServerResponse;

import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        String dir = "C:/Users/gusta/Downloads/earth.bmp";
        String dir2 = "C:/Users/gusta/Downloads/earth2.bmp";

        String test = "Hello World, Foo Bar in Katz Korner - Meow!";

        String b64 = Base64Tool.encodeString(test,true);

        String returnString = new String(Base64Tool.decodeString(b64,true));

        System.out.println(returnString);

        HttpServer server = new HttpServer(20512, new ServerAction() {
            @Override
            public ServerResponse respond(String path, List<Header> headers, String method, String data) {
                return new ServerResponse("200 OK",headers,data);
            }
        });

        server.start();
    }
}
