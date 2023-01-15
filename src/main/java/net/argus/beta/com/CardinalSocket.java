package net.argus.beta.com;

import java.io.IOException;

import net.argus.beta.com.pack.Package;
import net.argus.crypto.CryptoAES;

public class CardinalSocket {
	
	private Stream stream;
	private CryptoAES crypto;
	
	public CardinalSocket(Stream stream, CryptoAES crypto) {
		this.stream = stream;
		this.crypto = crypto;
	}
	
	public void send(Package pack) throws IOException {
		stream.send(crypto.encrypt(pack.toString()));
	}
	
	public Package nextPackage() throws IOException {
		return Package.parsePackage(crypto.decrypt(stream.read()));
	}
	
	public Stream getStream() {
		return stream;
	}

}
