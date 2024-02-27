package net.argus.crypto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoAES {
	
	public static final String ALGORITHM = "AES";
	
	private SecretKey key;
	
	public CryptoAES() {
		this(buildSecretKey());
	}
	
	public CryptoAES(SecretKey key) {
		this.key = key;
	}
	
	public byte[] encrypt(String text) {
		 try {
			  Cipher encryptCipher = Cipher.getInstance(ALGORITHM);
			  encryptCipher.init(Cipher.ENCRYPT_MODE, key);
		
			    return encryptCipher.doFinal(text.getBytes());
		 }catch(InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			 e.printStackTrace();
		 }
		 return text.getBytes();
	}
	
	public String decrypt(byte[] cipher) {
		try {
			 Cipher decryptCipher = Cipher.getInstance(ALGORITHM);
			 decryptCipher.init(Cipher.DECRYPT_MODE, key);

			return new String(decryptCipher.doFinal(cipher));
		 }catch(InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			 e.printStackTrace();
		 }
		 return new String(cipher);
	}
	
	private static String genPassword(int size) {
		String str = "";
		Random r = new Random();
		for(int i = 0; i < size; i++)
			str += new String(new byte[] {(byte) (r.nextInt(256) - 128)});
		
		return str;
	}
	
	public static SecretKey buildSecretKey() {
		try {
		    MessageDigest sha = MessageDigest.getInstance("SHA-256");
		    
		    byte[] key = sha.digest(genPassword(256).getBytes("UTF-8"));
		    SecretKey secret = new SecretKeySpec(key, ALGORITHM);
		    return secret;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
