package net.argus.beta.com;

import java.io.IOException;

import net.argus.crypto.CryptoAES;

public class CardinalSocket {
	
	private Stream stream;
	private CryptoAES crypto;
	
	public CardinalSocket(Stream stream, CryptoAES crypto) {
		this.stream = stream;
		this.crypto = crypto;
	}
	
	public void send(String pack) throws IOException {
		stream.send(crypto.encrypt(pack));
	}
	
	public String nextString() throws IOException {
		return (crypto.decrypt(stream.read()));
	}
	
	public Stream getStream() {
		return stream;
	}
	
	public void close() throws IOException {
		stream.close();
	}
	
	public boolean isClosed() {
		return stream.isClosed();
	}
	
	public CardinalSocket create() {
		return new CardinalSocket(stream, crypto);
	}

}
