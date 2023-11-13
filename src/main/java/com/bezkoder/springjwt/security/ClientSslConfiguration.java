package com.bezkoder.springjwt.security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.cert.X509Certificate;

@Configuration
public class ClientSslConfiguration {
    @Value("${client.ssl.key-store}")
    private Resource trustStore;
    @Value("${client.ssl.key-store-password}")
    private String trustStorePassword;

    @Bean
    public RestTemplate restTemplate() throws Exception {
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());

        // Construye el SSLContext utilizando SSLContexts.custom() o SSLContextBuilder según tu preferencia
        // Ejemplo con SSLContexts.custom():
        // SSLContext sslContext = SSLContexts.custom()
        //         .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
        //         .loadKeyMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
        //         .build();

        ClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
                }
                super.prepareConnection(connection, httpMethod);
            }
        };

        return new RestTemplate(factory);
    }
}
