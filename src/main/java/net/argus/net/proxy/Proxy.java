package net.argus.net.proxy;

import java.io.IOException;
import java.net.InetAddress;

public class Proxy {
	
	private InetAddress outputServer;
	private int outputPort, listenerPort;
	
	private ProxyServer server;
	
	public Proxy(InetAddress outputServer, int outputPort, int listenerPort) throws IOException {
		this.outputServer = outputServer;
		this.outputPort = outputPort;
		
		this.listenerPort = listenerPort;
		
		server = new ProxyServer(outputServer, outputPort, listenerPort);
	}
	
	public void open() {
		server.start();
	}
	
	public void close() throws IOException {
		server.close();
	}
	
	public InetAddress getOutputServer() {return outputServer;}
	public int getOutputPort() {return outputPort;}	

	public int getListenerPort() {return listenerPort;}
}
