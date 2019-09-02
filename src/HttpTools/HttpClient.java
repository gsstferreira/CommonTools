package HttpTools;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class HttpClient {

    private final boolean useCache;
    private final boolean proxy;
    private final boolean secure;
    private final int reqTimeout;
    private final int respTimeout;

    public HttpClient(boolean useCache, boolean proxy, boolean secure, int requestTimeout, int responseTimeout) {
        this.useCache = useCache;
        this.proxy = proxy;
        this.secure = secure;
        this.reqTimeout = requestTimeout;
        this.respTimeout = responseTimeout;
    }

    public boolean isResponseCached() {
        return useCache;
    }

    public boolean isUsingProxy() {
        return proxy;
    }

    public int getRequestTimeoutLimit() {
        return reqTimeout;
    }

    public int getResponseTimeoutLimit() {
        return respTimeout;
    }

    public boolean isSecure() {
        return secure;
    }

    public HttpResponse sendGetRequest(String url, List<Header> headers) throws IOException {

        URL _url = new URL(url);

        if(secure) {
            return httpRequestSecure(_url,headers,"GET");
        }
        else {
            return httpRequest(_url,headers,"GET");
        }
    }

    public HttpResponse sendDeleteRequest(String url, List<Header> headers) throws IOException {

        URL _url = new URL(url);

        if(secure) {
            return httpRequestSecure(_url,headers,"DELETE");
        }
        else {
            return httpRequest(_url,headers,"DELETE");
        }
    }

    public HttpResponse sendPostRequest(String url, List<Header> headers, byte[] data) throws IOException {

        URL _url = new URL(url);

        if(secure) {
            return httpRequestSecure(_url,headers,"POST",data);
        }
        else {
            return httpRequest(_url,headers,"POST",data);
        }
    }

    public HttpResponse sendPutRequest(String url, List<Header> headers,  byte[] data) throws IOException {

        URL _url = new URL(url);

        if(secure) {
            return httpRequestSecure(_url,headers,"PUT",data);
        }
        else {
            return httpRequest(_url,headers,"PUT",data);
        }
    }

    //Unsecure request without data
    private HttpResponse httpRequest(URL url, List<Header> headers, String method) throws IOException {

        BufferedReader streamReader;

        HttpURLConnection connection = setupConnection(url,headers,method);
        connection.setDoOutput(false);
        connection.connect();

        final int httpResponseCode = connection.getResponseCode();
        final List<Header> responseHeaders = Header.getHeaders(connection.getHeaderFields());

        try {
            streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e) {
            streamReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String currentLine;

        while((currentLine = streamReader.readLine()) != null) {
            sb.append(currentLine).append("\n");
        }

        connection.disconnect();

        try {
            Objects.requireNonNull(streamReader).close();
        }
        catch (IOException ignored){ }

        return new HttpResponse(responseHeaders,httpResponseCode,sb.toString().trim());
    }

    //Unsecure request with data
    private HttpResponse httpRequest(URL url, List<Header> headers, String method, byte[] data) throws IOException {

        BufferedReader streamReader;

        HttpURLConnection connection = setupConnection(url,headers,method);
        connection.setDoOutput(true);
        connection.connect();

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();

        final int httpResponseCode = connection.getResponseCode();
        final List<Header> responseHeaders = Header.getHeaders(connection.getHeaderFields());

        try {
            streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e) {
            streamReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String currentLine;

        while((currentLine = streamReader.readLine()) != null) {
            sb.append(currentLine).append("\n");
        }

        connection.disconnect();

        try {
            Objects.requireNonNull(streamReader).close();
        }
        catch (IOException ignored){ }

        return new HttpResponse(responseHeaders,httpResponseCode,sb.toString().trim());
    }

    //Secure request without data
    private HttpResponse httpRequestSecure(URL url, List<Header> headers, String method) throws IOException {

        BufferedReader streamReader;

        HttpsURLConnection connection = (HttpsURLConnection) setupConnection(url,headers,method);
        connection.setDoOutput(false);
        connection.connect();

        final int httpResponseCode = connection.getResponseCode();
        final List<Header> responseHeaders = Header.getHeaders(connection.getHeaderFields());

        try {
            streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e) {
            streamReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String currentLine;

        while((currentLine = streamReader.readLine()) != null) {
            sb.append(currentLine).append("\n");
        }

        connection.disconnect();
        try {
            Objects.requireNonNull(streamReader).close();
        }
        catch (IOException ignored){ }

        return new HttpResponse(responseHeaders,httpResponseCode,sb.toString().trim());
    }

    //Secure request with data
    private HttpResponse httpRequestSecure(URL url, List<Header> headers, String method, byte[] data) throws IOException {

        BufferedReader streamReader;

        HttpsURLConnection connection = (HttpsURLConnection) setupConnection(url,headers,method);
        connection.setDoOutput(true);
        connection.connect();

        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();

        final int httpResponseCode = connection.getResponseCode();
        final List<Header> responseHeaders = Header.getHeaders(connection.getHeaderFields());

        try {
            streamReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (IOException e) {
            streamReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String currentLine;

        while((currentLine = streamReader.readLine()) != null) {
            sb.append(currentLine).append("\n");
        }

        connection.disconnect();
        try {
            Objects.requireNonNull(streamReader).close();
        }
        catch (IOException ignored){ }

        return new HttpResponse(responseHeaders,httpResponseCode,sb.toString().trim());
    }

    private HttpURLConnection setupConnection(URL url, List<Header> headers, String method) throws IOException {

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setConnectTimeout(reqTimeout);
        conn.setReadTimeout(respTimeout);
        conn.setUseCaches(useCache);
        conn.setAllowUserInteraction(true);

        if(headers != null) {
            for (Header h:headers) {
                for (String value:h.getValues()) {
                    conn.addRequestProperty(h.getName(),value);
                }
            }
        }

        return conn;
    }
}
