package net.argus.crypto;

import java.math.BigInteger;
import java.util.Random;

import net.argus.util.Math;

@Deprecated
class Keys {
	
	private Key publicKey;
	private Key privateKey;
	
	Keys(Key publicKey, Key privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public static Keys genKeys(int eI) {
		int[] firstForP = Math.getFirstNumber(100, 199);
		int[] firstForQ = Math.getFirstNumber(200, 299);

		BigInteger p = new BigInteger(String.valueOf(firstForP[new Random().nextInt(firstForP.length - 1)]));
		BigInteger q = new BigInteger(String.valueOf(firstForQ[new Random().nextInt(firstForQ.length - 1)]));

		BigInteger n = p.multiply(q);
		
		BigInteger pSub1 = p.subtract(new BigInteger("" + 1));
		BigInteger qSub1 = q.subtract(new BigInteger("" + 1));
		
		BigInteger phiN = pSub1.multiply(qSub1);
		
		while(true) {
			BigInteger pgcd = phiN.gcd(new BigInteger(""+eI));
			
			if(pgcd.equals(BigInteger.ONE))
				break;
			
			eI++;
		}
		
		BigInteger e = new BigInteger(""+eI);
		BigInteger d = e.modInverse(phiN);
		
		Key publicKey = new Key(n, e);
		Key privateKey = new Key(n, d);
		
		return new Keys(publicKey, privateKey);
	}
	
	public Key getPublicKey() {return publicKey;}
	Key getPrivateKey() {return privateKey;}

}
