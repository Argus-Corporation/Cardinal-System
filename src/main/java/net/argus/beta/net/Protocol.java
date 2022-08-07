package net.argus.beta.net;

import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.List;

public class Protocol implements URLStreamHandlerFactory {
	
	private static List<Protocol> protocols = new ArrayList<Protocol>();
	
	private String protocol;
	private URLStreamHandler handler;
	private boolean register = false;
	
	public Protocol(String protocol, URLStreamHandler handler) {
		this.protocol = protocol;
		this.handler = handler;
		protocols.add(this);
	}
	
	public static void register() {
		for(Protocol p : protocols)
			p.add();
	}
	
	public void add() {
		if(!register)
			URL.setURLStreamHandlerFactory(this);
		register = true;
	}
	
	public static void createProtocol(String protocol, URLStreamHandler handler) {
		new Protocol(protocol, handler).add();
	}
	
	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		if(protocol.equals(this.protocol))
			return handler;
		return null;
	}

}
