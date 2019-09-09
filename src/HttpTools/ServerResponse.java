package HttpTools;

import java.util.List;

public class ServerResponse {

    private final String responseMethod;
    private final List<Header> responseHeaders;
    private final String responseData;

    public ServerResponse(StatusCode statusCode, List<Header> headers, String data) {

        this.responseMethod = statusCode.getCodeDescr();
        this.responseData = data;
        this.responseHeaders = headers;
    }

    public ServerResponse(StatusCode statusCode, List<Header> headers) {

        if(statusCode != StatusCode.OK) {
            this.responseMethod = statusCode.getCodeDescr();
            this.responseHeaders = headers;
            this.responseData = statusCode.getDefaultMessage();
        }
        else {
            throw new IllegalArgumentException("HTTP Status Code 200 requires a response body.");
        }
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
