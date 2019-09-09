package HttpTools;

public enum StatusCode {

    OK(200,"200 OK",null),
    NO_CONTENT(204,"204 No Content","Request Successful."),
    BAD_REQUEST(400, "400 Bad Request","Invalid request body or headers."),
    UNAUTHORIZED(401,"401 Unauthorized","Request is missing authentication."),
    PAYMENT_REQUIRED(402,"402 Payment Required","Request inaccessible without payment."),
    FORBIDDEN(403,"403 Forbidden","Client host or user not allowed to perform such request."),
    NOT_FOUND(404,"404 Not Found","There is no corresponding method or action to request path."),
    METHOD_NOT_ALLOWED(405,"405 Method Not Allowed","Request method is not allowed on requested action."),
    INTERNAL_SERVER_ERROR(500,"500 Internal Server Error","There was an error during the processing of the request."),
    NOT_IMPLEMENTED(501,"501 Not Implemented","Method exists but is not yet implemented."),
    BAD_GATEWAY(502,"502 Bad Gateway","Request was not able to reach destination server."),
    SERVICE_UNAVAILABLE(503,"503 Service Unavailable","Server is currently unable to process requests.");

    private int codeNumber;
    private String codeDescr;
    private String defaultMessage;

    StatusCode(int code, String descr, String defMessage) {
        this.codeNumber = code;
        this.codeDescr = descr;
        this.defaultMessage = defMessage;
    }

    public int getCodeNumber() {
        return codeNumber;
    }

    public String getCodeDescr() {
        return codeDescr;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
