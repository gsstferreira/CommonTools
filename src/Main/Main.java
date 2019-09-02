package Main;

import Base64Tools.*;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String helloW = "Hello World in Katz Korner!///////////////////////////////???????????????????????\\\\\\\\\\\\\\\\";
        byte[] bytes = helloW.getBytes();
        String b64_custom = Base64Tool.encodeBytes(bytes,true);

        System.out.println(b64_custom);

        String custom_decoded = new String(Base64Tool.decodeBytes(b64_custom.getBytes(),true));

        System.out.println();
        System.out.println(custom_decoded);
    }
}
