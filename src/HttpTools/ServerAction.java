package HttpTools;

import java.util.List;

public interface ServerAction {
    ServerResponse respond(String path, List<Header> headers, String method, String data);
}
