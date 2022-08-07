package net.argus.beta.net.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class CardinalSSLSocketFactory {
	
	private static final String PASSWORD = "";

	public static SSLServerSocket getServerSocket(int port) {
		InputStream inputStream = CardinalSSLSocketFactory.class.getResourceAsStream("/cert/PRIVATE_KEY.jks");
		if(inputStream == null)
			return null;
		
		try {
			return SSLServerSocketKeystoreFactory.getServerSocketWithCert(port, inputStream, PASSWORD);
		}catch(KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException
				| KeyStoreException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static SSLSocket getSocket(InetAddress addd, int port) {
		InputStream inputStream = CardinalSSLSocketFactory.class.getResourceAsStream("/cert/PUBLIC_KEY.jks");
		if(inputStream == null)
			return null;
		
		try {
			return SSLSocketKeystoreFactory.getSocketWithCert(addd, port, inputStream, PASSWORD);
		}catch(KeyManagementException | NoSuchAlgorithmException | CertificateException
				| KeyStoreException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
