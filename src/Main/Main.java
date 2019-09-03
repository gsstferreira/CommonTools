package Main;

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String dir = "C:/Users/gusta/Downloads/earth.bmp";
        String dir2 = "C:/Users/gusta/Downloads/earth2.bmp";

        File f = new File(dir);
        File f2 = new File(dir2);

        byte[] image = new BufferedInputStream(new FileInputStream(f)).readAllBytes();
        byte[] image2 = new BufferedInputStream(new FileInputStream(f2)).readAllBytes();

//        String s1 = Base64.getEncoder().encodeToString(image).trim();
//        String s2 = Base64Tool.encodeBytes(image,false);
//
//        System.out.println(s1.length() == s2.length());
//        System.out.println(s1.equals(s2));
//
//        for(int i = 0; i < s1.length(); i++) {
//
//            char c1 = s1.charAt(i);
//            char c2 = s2.charAt(i);
//
//            if(c1 != c2) {
//                System.out.println(String.format("Mismatch at position %08d: '%c' should be '%c'",i,c2,c1));
//            }
//        }
//
//        FileOutputStream outputStream = new FileOutputStream(new File("C:/Users/gusta/Downloads/earth_mesh.bmp"));
//
//        outputStream.write(diff_mesh);
//        outputStream.flush();
//        outputStream.close();

    }
}
