package net.argus.event.net.socket;

import net.argus.net.socket.SocketStatus;

public class SocketEvent {
	
	private Object obj;
	
	private String host;
	private int port;
	
	private String arg;
	
	private SocketStatus status;
	
	public SocketEvent(Object obj, String host, int port, String arg, SocketStatus status) {
		this.obj = obj;
		this.host = host;
		this.port = port;
		this.arg = arg;
		this.status = status;
	}
	
	public Object getObject() {return obj;}
	
	public String getHost() {return host;}
	public int getPort() {return port;}
	
	public String getArgument() {return arg;}
	
	public SocketStatus getStatus() {return status;}

}
