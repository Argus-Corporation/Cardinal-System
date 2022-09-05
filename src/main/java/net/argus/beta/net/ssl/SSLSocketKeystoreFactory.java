package net.argus.beta.net.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SSLSocketKeystoreFactory {
	
	private static X509TrustManager[] tm(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
	    TrustManagerFactory trustMgrFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	    trustMgrFactory.init(keystore);
	    TrustManager trustManagers[] = trustMgrFactory.getTrustManagers();
	    for (TrustManager trustManager : trustManagers) {
	        if (trustManager instanceof X509TrustManager) {
	            X509TrustManager[] tr = new X509TrustManager[1];
	            tr[0] = (X509TrustManager) trustManager;
	            return tr;
	        }
	    }
	    return null;
	}

	public static SSLSocket getSocketWithCert(InetAddress ip, int port, InputStream pathToCert, String passwordFromCert) throws IOException,
	       KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
	           X509TrustManager[] tmm;
	         
	           KeyStore ks  = KeyStore.getInstance("JKS");
	           ks.load(pathToCert, passwordFromCert.toCharArray());
	           tmm=tm(ks);
	          
	           SSLContext ctx = SSLContext.getInstance("TLSv1.2");
	           ctx.init(null, tmm, null);

	           SSLSocketFactory SocketFactory = ctx.getSocketFactory();
	           SSLSocket socket = (SSLSocket) SocketFactory.createSocket();
	           socket.connect(new InetSocketAddress(ip, port), 5000);
	           return socket;
	       }


}
