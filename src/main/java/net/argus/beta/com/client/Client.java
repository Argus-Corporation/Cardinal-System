package net.argus.beta.com.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import net.argus.beta.com.CardinalSocket;
import net.argus.beta.com.CardinalSocketFactory;

public class Client {
	
	private InetAddress host;
	private int port;
		
	public Client(String host, int port) throws UnknownHostException {
		this(InetAddress.getByName(host), port);
	}
	
	public Client(InetAddress serverAddress, int port) {
		this.host = serverAddress;
		this.port = port;
	}
	
	public CardinalSocket open() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		return CardinalSocketFactory.createClientConnection(host, port);
	}
	
	public int getPort() {
		return port;
	}
	
	public InetAddress getHost() {
		return host;
	}
	
}
