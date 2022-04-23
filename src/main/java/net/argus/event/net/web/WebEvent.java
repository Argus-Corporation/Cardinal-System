package net.argus.event.net.web;

import java.net.Socket;

public class WebEvent {
	
	private Socket socket;
	
	public WebEvent(Socket socket) {
		this.socket = socket;
	}
	
	public Socket getSocket() {return socket;}

}
