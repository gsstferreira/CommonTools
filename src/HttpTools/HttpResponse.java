package HttpTools;

import java.util.List;

public class HttpResponse {

    private final List<Header> headers;
    private final int responseCode;
    private final byte[] content;

    HttpResponse(List<Header> headers, int httpCode, String content) {
        this.headers = headers;
        this.responseCode = httpCode;

        this.content = content.getBytes();
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public byte[] getContentAsBytes() {
        return content;
    }

    public String getContentAsString() {
        return new String(content);
    }

    public static HttpResponse makeResponseError(String message) {
        return new HttpResponse(null,0,message);
    }

    public static HttpResponse makeResponseError(Throwable t) {
        return new HttpResponse(null,0,t.getLocalizedMessage());
    }
}
