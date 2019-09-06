package HttpTools;

import java.util.List;

public class ServerResponse {

    private final String responseMethod;
    private final List<Header> responseHeaders;
    private final String responseData;

    public ServerResponse(String method, List<Header> headers, String data) {

        this.responseMethod = method;
        this.responseData = data;
        this.responseHeaders = headers;
    }

    public String getResponseMethod() {
        return responseMethod;
    }

    public List<Header> getResponseHeaders() {
        return responseHeaders;
    }

    public String getResponseData() {
        return responseData;
    }
}
