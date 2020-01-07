package HttpTools;

import java.net.Socket;
import java.util.List;

public interface ServerAction {

    AuthResult authorize(Socket socket, String path, List<Header> headers, String method);
    ServerResponse respondGET(String path, List<Header> headers);
    ServerResponse respondPOST(String path, List<Header> headers, String data);
    ServerResponse respondPUT(String path, List<Header> headers, String data);
    ServerResponse respondDELETE(String path, List<Header> headers);
    ServerResponse respondCustom(String path, List<Header> headers, String method, String data);
}
