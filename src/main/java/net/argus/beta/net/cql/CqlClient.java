package net.argus.beta.net.cql;

import java.io.IOException;

import net.argus.beta.net.ctp.CtpURLConnection;

public class CqlClient {
	
	public static final int DEFAULT_PORT = CtpURLConnection.DEFAULT_CTP_PORT;
	
	private String host;
	private int port;
	
	private CqlSender sender;
		
	public CqlClient(String host) {
		this(host, DEFAULT_PORT);
	}
		
	public CqlClient(String host, int port) {
		this.host = host;
		this.port = port;
		
		this.sender = new CqlSender(host, port);
	}
	
	public void connect(String userName, String password) throws IOException {
		sender.connect(userName, password);
	}
	
	public void query(String query) throws IOException {
		sender.sendQuery(query);
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}

}
