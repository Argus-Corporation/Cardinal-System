package net.argus.net.web;

import java.io.IOException;

import net.argus.instance.Instance;
import net.argus.util.Error;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class WebConnector extends Thread {
	
	private WebServer server;
	
	public WebConnector(WebServer server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Instance.startThread(new WebConnection(server.getServer().accept(), server));
			}catch(IOException e) {Error.createErrorFileLog(e); Debug.log("Error on connection", Info.ERROR);}
		}
	}

}
