package HttpTools;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;

public class HttpResponseSecure extends HttpResponse {

    private HostnameVerifier hostnameVerifier;
    private Certificate[] localCertificates;
    private Certificate[] serverCertificates;
    private String cipherSuite;
    private Principal localPrincipal;
    private Principal peerPrincipal;

    private boolean peerVerified;

    HttpResponseSecure(List<Header> headers, int httpCode, String content, HttpsURLConnection connection) {
        super(headers,httpCode,content);

        if(connection != null) {
            hostnameVerifier = connection.getHostnameVerifier();
            localCertificates = connection.getLocalCertificates();
            cipherSuite = connection.getCipherSuite();
            localPrincipal = connection.getLocalPrincipal();

            try {
                serverCertificates = connection.getServerCertificates();
                peerPrincipal = connection.getPeerPrincipal();
                peerVerified = true;
            }
            catch (SSLPeerUnverifiedException e) {
                peerVerified = false;
                serverCertificates = null;
                peerPrincipal = null;
            }
        }
        else {
            hostnameVerifier = null;
            localCertificates = null;
            serverCertificates = null;
            cipherSuite = null;
            localPrincipal = null;
            peerPrincipal = null;
            peerVerified = false;
        }

    }

    public static HttpResponseSecure makeResponseError(String message) {
        return new HttpResponseSecure(null,0,message,null);
    }

    public static HttpResponseSecure makeResponseError(Throwable t) {
        return new HttpResponseSecure(null,0,t.getLocalizedMessage(),null);
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public Certificate[] getLocalCertificates() {
        return localCertificates;
    }

    public Certificate[] getServerCertificates() {
        return serverCertificates;
    }

    public String getCipherSuite() {
        return cipherSuite;
    }

    public Principal getLocalPrincipal() {
        return localPrincipal;
    }

    public Principal getPeerPrincipal() {
        return peerPrincipal;
    }

    public boolean isPeerVerified() {
        return peerVerified;
    }
}
