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
	
	/**
	 * Ca c'est une fonction utilitaire permettant de r�cup�rer le "v�rifieur de certificat", on prend uniquement celui correspondant au certificat pour minimiser le temps de chargement � la connexion
	 * 
	 * Pas tr�s int�ressante...
	 */
	private static X509TrustManager[] tm(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
	    TrustManagerFactory trustMgrFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	    trustMgrFactory.init(keystore);
	    //on prend tous les managers
	    TrustManager trustManagers[] = trustMgrFactory.getTrustManagers();
	    for (TrustManager trustManager : trustManagers) {
	        if (trustManager instanceof X509TrustManager) {
	            X509TrustManager[] tr = new X509TrustManager[1];
	            //on renvoie juste celui que l'on va utiliser
	            tr[0] = (X509TrustManager) trustManager;
	            return tr;
	        }
	    }
	    return null;
	}

	//Le vrai du code 

	public static SSLSocket getSocketWithCert(InetAddress ip, int port, InputStream pathToCert, String passwordFromCert) throws IOException,
	       KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
	           X509TrustManager[] tmm;
	           //ATTENTION
	           //Android, remplacez JKS par BKS :)
	           //On charge le lecteur de Keystore en fonction du format
	           KeyStore ks  = KeyStore.getInstance("JKS");
	           //On charge le Keystore avec sont stream et son mot de passe
	           ks.load(pathToCert, passwordFromCert.toCharArray());                        //On d�marre le gestionnaire de validation des cl�s
	           tmm=tm(ks);
	           //On d�marre le contexte, autrement dit le langage utilis� pour crypter les donn�es
	           //On peut replacer TLSv1.2 par SSL
	           SSLContext ctx = SSLContext.getInstance("TLSv1.2");
	           ctx.init(null, tmm, null);
	           //On cr�ee enfin la socket en utilisant une classe de cr�ation SSLSocketFactory, vers l'adresse et le port indiqu�
	           SSLSocketFactory SocketFactory = ctx.getSocketFactory();
	           SSLSocket socket = (SSLSocket) SocketFactory.createSocket();
	           socket.connect(new InetSocketAddress(ip, port), 5000);
	           return socket;
	       }


}
