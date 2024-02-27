package net.argus.crypto;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class CryptoRSA {
	
	public static final String ALGORITHM = "RSA";
	
	private KeyPair pair;
	
	public CryptoRSA() {
		this(1024);
	}
	
	public CryptoRSA(int keySize) {
		pair = buildKeyPair(keySize);
	}
	
	public void setPublicKey(byte[] publicKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory publicKeyFactory = KeyFactory.getInstance(ALGORITHM);
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey publicKey = publicKeyFactory.generatePublic(publicKeySpec);		
		
		pair = new KeyPair(publicKey, pair.getPrivate());
	}
	
	public byte[] encrypt(String text) {
		return encrypt(text.getBytes());
	}
	
	public byte[] encrypt(byte[] text) {
		 try {
			    Cipher encryptCipher = Cipher.getInstance(ALGORITHM);
			    encryptCipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
		
			    return encryptCipher.doFinal(text);
		 }catch(InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			 e.printStackTrace();
		 }
		 return text;
	}
	
	public String decryptString(byte[] cipher) {
		return new String(decrypt(cipher));
	}
	
	
	public byte[] decrypt(byte[] cipher) {
		try {
			 Cipher decryptCipher = Cipher.getInstance(ALGORITHM);
			 decryptCipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());

			return decryptCipher.doFinal(cipher);
		 }catch(InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			 e.printStackTrace();
		 }
		 return cipher;
	}
	
	public PublicKey getPublicKey() {
		return pair.getPublic();
	}
	
	public KeyPair buildKeyPair(int keySize) {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
			keyPairGenerator.initialize(keySize);
			return keyPairGenerator.generateKeyPair();
			
		}catch(NoSuchAlgorithmException e) {e.printStackTrace();}
		return null;
	}
	 
}
