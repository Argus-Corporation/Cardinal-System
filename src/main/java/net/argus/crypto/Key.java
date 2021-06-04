package net.argus.crypto;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import net.argus.number.Hexadecimal;

public class Key {
	
	private BigInteger a, b;
	
	public Key(BigInteger a, BigInteger b) {
		this.a = a;
		this.b = b;
	}
	
	/**--CRYPT--**/
	public BigInteger crypt(char cr) {return crypt((int) cr);}
	
	public String crypt(String m) {
		byte[] b = m.getBytes(StandardCharsets.UTF_8);
		
		String c = "";
		for(int i = 0; i < b.length; i++)
			c += Hexadecimal.valueOf(crypt((b[i] * (i>2?i:i+2))).intValue()) + " ";
		
		return c;
	}
	
	public BigInteger crypt(int cr) {
		BigInteger c = new BigInteger(""+cr).pow(b.intValue());
		c = c.subtract(c.divide(a).multiply(a));
		
		return c;
	}
	
	/**--DECRYPT--**/
	public char decryptChar(BigInteger cr) {return (char) decrypt(cr).intValue();}
	
	public String decryptString(String cr) {
		String[] part = cr.split(" ");
		
		byte[] bs = new byte[part.length];
		for(int i = 0; i < part.length; i++) {
			BigInteger c = new BigInteger(String.valueOf(new Hexadecimal(part[i]).toInt()));
			BigInteger m = decrypt(c);
			
			byte b = m.divide(new BigInteger((i>2?i:i+2)+"")).byteValue();
			
			bs[i] = b;
		}
		return new String(bs, StandardCharsets.UTF_8);
	}
	
	public BigInteger decrypt(BigInteger cr) {
		BigInteger c = new BigInteger(cr.toString()).pow(b.intValue());
		c = c.subtract(c.divide(a).multiply(a));
		
		return c;
	}
	
	public BigInteger getA() {return a;}
	public BigInteger getB() {return b;}
	
	
}
