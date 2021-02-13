package net.argus.event.socket;

public class SocketEvent {
	
	private Object obj;
	
	private String host;
	private int port;
	
	public SocketEvent(Object obj, String host, int port) {
		this.obj = obj;
		this.host = host;
		this.port = port;
	}
	
	public Object getObject() {return obj;}
	
	public String getHost() {return host;}
	public int getPort() {return port;}

}
