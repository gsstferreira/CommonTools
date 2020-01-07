package HttpTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerResponse {

    private final String responseCode;
    private final List<Header> responseHeaders;
    private final String responseBody;

    /**
    Creates a new instance of ServerResponse with specified HTTP status code, response headers and response body.
    @param statusCode HTTP status code of response
    @param headers List of custom response headers
    @param body Response data/body (as string)
    */
    public ServerResponse(StatusCode statusCode, List<Header> headers, String body) {
        this.responseCode = statusCode.getCodeDescr();
        this.responseBody = body;
        this.responseHeaders = headers == null ? stdHeaders() : headers;
    }

    /**
    Creates a new instance of ServerResponse with specified HTTP status code and response headers,
    as well as a standard response data to given code.
    @param statusCode HTTP status code of response
    @param headers List of custom response headers
    @throws IllegalArgumentException if HTTP status code 200 is used as parameter (requires custom response body)
    */
    public ServerResponse(StatusCode statusCode, List<Header> headers) {
        if(statusCode != StatusCode.OK) {
            this.responseCode = statusCode.getCodeDescr();
            this.responseHeaders = headers == null ? stdHeaders() : headers;
            this.responseBody = statusCode.getDefaultMessage();
        }
        else {
            throw new IllegalArgumentException("HTTP Status Code 200 requires a non-null & non-empty response body.");
        }
    }

    /**
    Creates a new instance of ServerResponse with specified HTTP status code and standard response headers for date and content type,
    as well as a standard response data to given code.
    @param statusCode HTTP status code of response
    @throws IllegalArgumentException if HTTP status code 200 is used as parameter (requires custom response body)
    */
    public ServerResponse(StatusCode statusCode) {
        if(statusCode != StatusCode.OK) {

            this.responseCode = statusCode.getCodeDescr();
            this.responseHeaders = stdHeaders();
            this.responseBody = statusCode.getDefaultMessage();
        }
        else {
            throw new IllegalArgumentException("HTTP Status Code 200 requires a non-null & non-empty response body.");
        }
    }

    /**
     Creates a new instance of ServerResponse from HttpResponse, extracting response code, heaqders and content.
     @param response HTTP response
     */
    public ServerResponse(HttpResponse response) {

        String code = null;

        for (StatusCode sc:StatusCode.values()) {
            if(sc.getCodeNumber() == response.getResponseCode()) {
                code = sc.getCodeDescr();
                break;
            }
        }

        List<Header> hs = response.getHeaders();

        this.responseHeaders = hs == null ? stdHeaders() : hs;

        if(code == null) {
            this.responseCode = StatusCode.BAD_GATEWAY.getCodeDescr();
            this.responseBody = StatusCode.BAD_GATEWAY.getDefaultMessage();
        }
        else {
            this.responseCode = code;
            this.responseBody = response.getContentAsString();
        }
    }
    

    /**
     Returns response code and description as String.
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     Returns list of response headers as List<Header>.
     */
    public List<Header> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     Returns response body as String.
     */
    public String getResponseBody() {
        return responseBody;
    }

    private List<Header> stdHeaders() {
        List<Header> headers = new ArrayList<>();
        headers.add(new Header("Date",new Date().toString()));
        headers.add(new Header("Content-Type","text/plain"));

        return headers;
    }
}
