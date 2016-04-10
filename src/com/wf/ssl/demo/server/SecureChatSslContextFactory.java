package com.wf.ssl.demo.server;


import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;

public final class SecureChatSslContextFactory {

    private static final String PROTOCOL = "TLS";
    private static final SSLContext SERVER_CONTEXT;
    private static final SSLContext CLIENT_CONTEXT;

    private static String CLIENT_KEY_STORE = "E:\\ssl\\cChat.jks";
    private static String CLIENT_TRUST_KEY_STORE = "E:\\ssl\\wf.jks";
    private static String CLIENT_KEY_STORE_PASSWORD = "cNetty";
    private static String CLIENT_TRUST_KEY_STORE_PASSWORD = "sNetty";

    private static String SERVER_KEY_STORE = "E:\\ssl\\wf.jks";
    private static String SERVER_TRUST_KEY_STORE = "E:\\ssl\\cChat.jks";
    private static String SERVER_KEY_STORE_PASSWORD = "sNetty";
    private static String SERVER_TRUST_KEY_STORE_PASSWORD = "cNetty";

    static {
        String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (algorithm == null) {
            algorithm = "SunX509";
        }

        SSLContext serverContext;
        SSLContext clientContext = null;
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(SERVER_KEY_STORE), SERVER_KEY_STORE_PASSWORD.toCharArray());
            KeyStore tks = KeyStore.getInstance("JKS");
            tks.load(new FileInputStream(SERVER_TRUST_KEY_STORE), SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());

            // Set up key manager factory to use our key store
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);

            // Initialize the SSLContext to work with our key managers.
            serverContext = SSLContext.getInstance(PROTOCOL);
//            serverContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            serverContext.init(kmf.getKeyManagers(), null, null);

        } catch (Exception e) {
            throw new Error("Failed to initialize the server-side SSLContext", e);
        }

        KeyStore ks2 = null;
        try {
            ks2 = KeyStore.getInstance("JKS");
            ks2.load(new FileInputStream(CLIENT_KEY_STORE), CLIENT_KEY_STORE_PASSWORD.toCharArray());

    /*        KeyStore tks2 = KeyStore.getInstance("JKS");
            tks2.load(new FileInputStream(CLIENT_TRUST_KEY_STORE), CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());
            // Set up key manager factory to use our key store
            KeyManagerFactory kmf2 = KeyManagerFactory.getInstance(algorithm);
            TrustManagerFactory tmf2 = TrustManagerFactory.getInstance("SunX509");
            kmf2.init(ks2, CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tmf2.init(tks2);
            clientContext = SSLContext.getInstance(PROTOCOL);
            clientContext.init(kmf2.getKeyManagers(), tmf2.getTrustManagers(), null);*/
        } catch (Exception e) {
            throw new Error("Failed to initialize the client-side SSLContext", e);
        }

        try {
            KeyStore trustStore = null;
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            TrustManager[] trustManager = trustManagerFactory.getTrustManagers();

            KeyManager[] keyManager = null;
            if (ks2 != null) {
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                String storePassword = CLIENT_TRUST_KEY_STORE;
                keyManagerFactory.init(ks2, storePassword.toCharArray());
                keyManager = keyManagerFactory.getKeyManagers();
            }

            clientContext = SSLContext.getInstance("SSL", "SunJSSE");
            clientContext.init(keyManager, trustManager, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SERVER_CONTEXT = serverContext;
        CLIENT_CONTEXT = clientContext;
    }

    public static SSLContext getServerContext() {
        return SERVER_CONTEXT;
    }

    public static SSLContext getClientContext() {
        return CLIENT_CONTEXT;
    }

    private SecureChatSslContextFactory() {
        // Unused
    }
}
