package net.argus.net.web;

import java.io.IOException;
import java.net.ServerSocket;

import net.argus.event.net.web.EventWeb;
import net.argus.event.net.web.WebListener;
import net.argus.instance.Instance;
import net.argus.util.debug.Debug;

public class WebServer {
	
	public static final int PORT = 11067;
	
	private ServerSocket server;
	private WebConnector connector;
	
	private EventWeb event = new EventWeb();
	
	public WebServer() throws IOException {
		this(PORT);
	}
	
	public WebServer(int port) throws IOException {
		server = new ServerSocket(port);
		connector = new WebConnector(this);
	}
	
	public void open() {
		Instance.startThread(connector);
		Debug.log("Web Server opened");
	}
	
	public void addWebListener(WebListener listener) {event.addListener(listener);}
	
	public ServerSocket getServer() {return server;}
	
	protected EventWeb getEvent() {return event;}

}
