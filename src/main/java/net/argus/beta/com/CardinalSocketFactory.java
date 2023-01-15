package net.argus.beta.com;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import net.argus.crypto.CryptoAES;
import net.argus.crypto.CryptoRSA;

public class CardinalSocketFactory {
	
	public static CardinalSocket createServerConnection(Socket client, CryptoRSA rsa) throws IOException {
		Stream stream = new Stream(client);
		
		//--**CRYPTO**--\\
		stream.send(rsa.getPublicKey().getEncoded());  // envoie de la cl� public
		CryptoAES crypto = new CryptoAES(new SecretKeySpec(rsa.decrypt(stream.read()), CryptoAES.ALGORITHM));  // d�cryptage et cr�ation de la cl� et du crypto sym�trique pour communiquer avec le client
		
		return new CardinalSocket(stream, crypto);
	}
	
	public static void createClientConnection(String host, int port) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		createClientConnection(InetAddress.getByName(host), port);
	}

	
	public static CardinalSocket createClientConnection(InetAddress host, int port) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		Socket socket = new Socket(host, port);  // ouverture de la connection
		Stream stream = new Stream(socket);
		
		
		//--**CRYPTO**--\\
		SecretKey key = CryptoAES.buildSecretKey();  // g�n�ration de la cl� sym�trique
		CryptoAES crypto = new CryptoAES(key);  // cr�ation de l'objet crypto pour encrypter les messages
		
		CryptoRSA serverC = new CryptoRSA();  // creation de l'objet rsacrypto du server pour encrypter la cl� sym�trique du client
		serverC.setPublicKey(stream.read());  // mise en place de la 'public key' du server
		stream.send(serverC.encrypt(key.getEncoded()));  // envoie de la cl� sym�trique encrypter

		return new CardinalSocket(stream, crypto);
	}
	

}
