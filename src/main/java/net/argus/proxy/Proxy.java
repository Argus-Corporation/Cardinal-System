package net.argus.proxy;

import java.io.IOException;
import java.net.ServerSocket;

public class Proxy {
	
	private ServerSocket proxy;
	
	public Proxy(int port) throws IOException {
		proxy = new ServerSocket(port);
		
		ClientProxy.connect(proxy);
	}
	
	public static void main(String[] args) throws IOException {
		new Proxy(11066);
	}

}
