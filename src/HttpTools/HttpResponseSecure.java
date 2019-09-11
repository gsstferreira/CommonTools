package HttpTools;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;

public class HttpResponseSecure extends HttpResponse {

    private final HostnameVerifier hostnameVerifier;
    private final Certificate[] localCertificates;
    private final Certificate[] serverCertificates;
    private final String cipherSuite;
    private final Principal localPrincipal;
    private final Principal peerPrincipal;

    private final boolean peerVerified;

    public HttpResponseSecure(List<Header> headers, int httpCode, String content, HttpsURLConnection connection) {
        super(headers,httpCode,content);

        Principal peerPrincipal1;
        Certificate[] serverCertificates1;
        boolean peerVerified1;

        if(connection != null) {
            hostnameVerifier = connection.getHostnameVerifier();
            localCertificates = connection.getLocalCertificates();
            cipherSuite = connection.getCipherSuite();
            localPrincipal = connection.getLocalPrincipal();

            try {
                serverCertificates1 = connection.getServerCertificates();
                peerPrincipal1 = connection.getPeerPrincipal();
                peerVerified1 = true;
            }
            catch (SSLPeerUnverifiedException e) {
                peerVerified1 = false;
                serverCertificates1 = null;
                peerPrincipal1 = null;
            }
        }
        else {
            hostnameVerifier = null;
            localCertificates = null;
            serverCertificates1 = null;
            cipherSuite = null;
            localPrincipal = null;
            peerPrincipal1 = null;
            peerVerified1 = false;
        }

        peerPrincipal = peerPrincipal1;
        serverCertificates = serverCertificates1;
        peerVerified = peerVerified1;
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
