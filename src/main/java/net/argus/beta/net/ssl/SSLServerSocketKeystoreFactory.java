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
	
	/**
	 * Ca c'est une fonction utilitaire permettant de r�cup�rer le "v�rifieur de certificat", on prend uniquement celui correspondant au certificat pour minimiser le temps de chargement � la connexion
	 * 
	 * Pas tr�s int�ressante...
	 */
	private static X509TrustManager tm(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
	    TrustManagerFactory trustMgrFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	    trustMgrFactory.init(keystore);
	    //on prend tous les managers
	    TrustManager trustManagers[] = trustMgrFactory.getTrustManagers();
	    for (int i = 0; i < trustManagers.length; i++) {
	        if (trustManagers[i] instanceof X509TrustManager) {
	            //on renvoie juste celui que l'on va utiliser
	            return (X509TrustManager) trustManagers[i];
	        }
	    }
	    return null;
	};
	/**
	 * Ca c'est une fonction utilitaire permettant de r�cup�rer le "gestionnaire de mot de passes des cl�s" (en gros)
	 * 
	 * Pas tr�s int�ressante...
	 */
	private static X509KeyManager km(KeyStore keystore, String password) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException {
	    KeyManagerFactory keyMgrFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	    keyMgrFactory.init(keystore, password.toCharArray());
	    //on prend tous les managers
	    KeyManager keyManagers[] = keyMgrFactory.getKeyManagers();
	    for (int i = 0; i < keyManagers.length; i++) {
	        if (keyManagers[i] instanceof X509KeyManager) {
	            //on renvoie juste celui que l'on va utiliser
	            return (X509KeyManager) keyManagers[i];
	        }
	    }
	    return null;
	};



	/**
	 * Le vrai morceau, que l'on utilisera
	 */
	public static SSLServerSocket getServerSocketWithCert(int port, InputStream pathToCert, String passwordFromCert) throws IOException,
	       KeyManagementException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException{
	           TrustManager[] tmm = new TrustManager[1];
	           KeyManager[] kmm = new KeyManager[1];
	           //On charge le lecteur de Keystore en fonction du format
	           //ATTENTION
	           //POUR LES SERVEURS android, remplacez le JKS par BKS, si c'est juste le client qui est android, laissez JKS
	           KeyStore ks  = KeyStore.getInstance("JKS");
	           //On charge le Keystore avec sont stream et son mot de passe
	           ks.load(pathToCert, passwordFromCert.toCharArray());
	           //On lance les gestionnaires de cl�s et de v�rification des clients
	           tmm[0]=tm(ks);
	           kmm[0]=km(ks, passwordFromCert);
	           //On d�marre le contexte, autrement dit le langage utilis� pour crypter les donn�es
	           //ATTENTION
	           //Ici, on peut remplacer  TLSv1.2 par SSL, mais il faudra le faire aussi bien dans le client que le serveur
	           SSLContext ctx = SSLContext.getInstance("TLSv1.2");
	           ctx.init(kmm, tmm, null);
	           //On lance la serversocket sur le port indiqu�, avec le contexte fourni
	           SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) ctx.getServerSocketFactory();
	           return (SSLServerSocket) socketFactory.createServerSocket(port);
	       }


}
