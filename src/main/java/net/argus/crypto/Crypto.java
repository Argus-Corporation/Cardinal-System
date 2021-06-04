package net.argus.crypto;

import java.util.Random;

import net.argus.util.debug.Debug;

public class Crypto {
	
	private Keys keys;
	
	public Crypto(int e) {
		this.keys = Keys.genKeys(e);
	}
	
	public  static Crypto genCrypto() {
		return new Crypto(new Random().nextInt(1000 - 100) + 100);
	}
	
	public CryptoValue crypt(int cr) {return new CryptoValue(keys.getPublicKey().crypt(cr));}
	public CryptoValue crypt(char cr) {return new CryptoValue(keys.getPublicKey().crypt(cr));}
	public String crypt(String c) {return keys.getPublicKey().crypt(c);}
	
	public CryptoValue decrypt(CryptoValue cr) {return new CryptoValue(keys.getPrivateKey().decrypt(cr.getValue()));}
	public String decryptString(String c) {return keys.getPrivateKey().decryptString(c);}
	public char decryptChar(CryptoValue cr) {return keys.getPrivateKey().decryptChar(cr.getValue());}
	
	public Key getPublicKey() {return keys.getPublicKey();}
	
	public static void main(String[] args) {
		Crypto cry = genCrypto();

		CryptoValue cr = cry.crypt('q');
		System.out.println(cr);
		Debug.log(cry.decryptChar(cr));
	}

}
