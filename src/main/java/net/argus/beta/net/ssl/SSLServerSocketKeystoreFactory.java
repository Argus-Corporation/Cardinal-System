package net.argus.beta.net.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

public class SSLServerSocketKeystoreFactory {
	
	private static X509TrustManager tm(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
	    TrustManagerFactory trustMgrFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	    trustMgrFactory.init(keystore);
	    TrustManager trustManagers[] = trustMgrFactory.getTrustManagers();
	    for (int i = 0; i < trustManagers.length; i++) {
	        if (trustManagers[i] instanceof X509TrustManager) {
	            return (X509TrustManager) trustManagers[i];
	        }
	    }
	    return null;
	};
	
	private static X509KeyManager km(KeyStore keystore, String password) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
	    KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	    keyMgrFactory.init(keystore, password.toCharArray());

	    KeyManager keyManagers[] = keyMgrFactory.getKeyManagers();
	    for (int i = 0; i < keyManagers.length; i++) {
	        if (keyManagers[i] instanceof X509KeyManager) {
	        	return (X509KeyManager) keyManagers[i];
	        }
	    }
	    return null;
	};

	public static SSLServerSocket getServerSocketWithCert(int port, InputStream pathToCert, String passwordFromCert) throws IOException,
	       KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException{
	           TrustManager[] tmm = new TrustManager[1];
	           KeyManager[] kmm = new KeyManager[1];
	           
	           KeyStore ks  = KeyStore.getInstance("JKS");

	           ks.load(pathToCert, passwordFromCert.toCharArray());

	           tmm[0]=tm(ks);
	           kmm[0]=km(ks, passwordFromCert);
	          
	           SSLContext ctx = SSLContext.getInstance("TLSv1.2");
	           ctx.init(kmm, tmm, null);

	           SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) ctx.getServerSocketFactory();
	           return (SSLServerSocket) socketFactory.createServerSocket(port);
	       }


}
