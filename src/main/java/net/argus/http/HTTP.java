package net.argus.http;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;

import net.argus.beta.net.ssl.CardinalSSLSocketFactory;
import net.argus.system.InitializationSystem;

public class HTTP {
	
	private SSLServerSocket server;
	
	private boolean running;
	
	public HTTP() throws IOException {
		server = CardinalSSLSocketFactory.getServerSocket(443);
	}
	
	public void start() throws IOException {
		running = true;
		loop();
	}
	
	public void loop() throws IOException {
		Runnable r = () -> {
			while(running)
				try {new HTTPClient(server.accept());}catch(IOException e) {e.printStackTrace();}
		};
		new Thread(r).start();
		
	}
	
	public static void main(String[] args) throws IOException {
		InitializationSystem.initSystem(args);
		new HTTP().start();
	}

}
