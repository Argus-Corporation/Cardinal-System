package net.argus.beta.net.ssl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

import net.argus.system.UserSystem;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class CardinalSSLSocketFactory {
	
	private static final String PASSWORD = "";

	public static SSLServerSocket getServerSocket(int port) throws FileNotFoundException {
		InputStream inputStream = getDefaultCertInputStream("PRIVATE_KEY.jks");
		if(inputStream == null) {
			Debug.log("Certificat file \"" + getDefaultCertPath() + "\" dosn't exist !", Info.ERROR);
			return null;
		}
		
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
	
	public static InputStream getDefaultCertInputStream(String certName) throws FileNotFoundException {
		String certPath = getDefaultCertPath();
		if(certPath == null)
			return null;
		
		if(UserSystem.getBooleanProperty("isLocalSSLCert"))
			return CardinalSSLSocketFactory.class.getResourceAsStream(certPath + certName);
		else
			return new FileInputStream(new File(certPath + certName));
		
	}
	
	public static String getDefaultCertPath() {
		String certPath = UserSystem.getProperty("SSLCertPath");
		if(certPath == null)
			return null;
		
		if(!certPath.endsWith("/"))
			certPath = certPath + "/";
		if(UserSystem.getBooleanProperty("isLocalSSLCert")) {
			if(!certPath.startsWith("/"))
				certPath = "/" + certPath;
			
			return certPath;
		}else {
			File f = new File(certPath);
			if(!f.isDirectory())
				return null;
			
			return certPath;
		}
	}
	
}
