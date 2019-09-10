package HttpTools;

import HttpTools.Header;

import java.util.List;

public class HttpResponse {

    private final List<Header> headers;
    private final int responseCode;
    private final byte[] content;

    public HttpResponse(List<Header> headers, int httpCode, String content) {
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

    public boolean isSuccessful() {
        return (responseCode >= 200) && (responseCode < 300);
    }

}
