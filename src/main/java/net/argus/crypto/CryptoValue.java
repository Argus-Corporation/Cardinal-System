package net.argus.crypto;

import java.math.BigInteger;

public class CryptoValue {
	
	private BigInteger value;
	
	public CryptoValue(BigInteger value) {
		this.value = value;
	}
	
	public BigInteger getValue() {return value;}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
