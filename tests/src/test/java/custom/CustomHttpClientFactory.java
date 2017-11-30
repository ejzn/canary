
package canary;

import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.net.SocketFactory;
import java.security.KeyStore;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.HttpClient;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;

import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.config.AbstractFactoryBean;


public class CustomHttpClientFactory extends AbstractFactoryBean<HttpClient> {

    @Override
    public Class<?> getObjectType() {
        return HttpClient.class;
    }

    @Override
    protected HttpClient createInstance() throws Exception {
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        TrustStrategy allTrust = new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        };

        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore, allTrust).build();
        

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        return httpClient;
    }
}