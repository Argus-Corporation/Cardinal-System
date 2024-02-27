package net.argus.beta.com;

import java.io.IOException;

import net.argus.beta.com.pack.Package;
import net.argus.crypto.CryptoAES;

public class NetSocket extends CardinalSocket {
	
	public NetSocket(Stream stream, CryptoAES crypto) {
		super(stream, crypto);
	}
	
	public void send(Package pack) throws IOException {
		super.send(pack.toString());
	}
	
	public Package nextPackage() throws IOException {
		return Package.parsePackage(super.nextString());
	}
	

}
