package Base64Tools;

import java.io.IOException;

public abstract class Base64Tool {

    public static String encodeString(String string, boolean isUrlEncoded) {
        if(isUrlEncoded) {
            return Methods.encodeBytes_UrlEncoding(string.getBytes());
        }
        else {
            return Methods.encodeBytes(string.getBytes());
        }
    }

    public static String encodeBytes(byte[] bytes, boolean isUrlEncoded) {
        if(isUrlEncoded) {
            return Methods.encodeBytes_UrlEncoding(bytes);
        }
        else {
            return Methods.encodeBytes(bytes);
        }
    }

    public static byte[] decodeString(String b64String, boolean isUrlEncoded) throws IOException {
        if(isUrlEncoded) {
            return Methods.decodeBytes_UrlEncoding(b64String.getBytes());
        }
        else {
            return Methods.decodeBytes(b64String.getBytes());
        }
    }

    public static byte[] decodeBytes(byte[] bytes, boolean isUrlEncoded) throws IOException {
        if(isUrlEncoded) {
            return Methods.decodeBytes_UrlEncoding(bytes);
        }
        else {
            return Methods.decodeBytes(bytes);
        }
    }
}
