package HttpTools;

import java.net.Socket;
import java.util.List;

public interface ServerAction {
    ServerResponse respond(Socket socket, String path, List<Header> headers, String method, String data);
}
