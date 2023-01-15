package net.argus.beta.com.server;

import java.io.IOException;
import java.net.ServerSocket;

import net.argus.beta.com.CardinalSocket;
import net.argus.beta.com.CardinalSocketFactory;
import net.argus.crypto.CryptoRSA;
import net.argus.instance.Instance;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class Server {
	
	public static final Instance SERVER_CONNECTOR = new Instance("server-connector");
	
	private int port;
	
	private ServerSocket serverSocket;
	
	public Server(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(port);
	}
	
	public void open() {
		Instance.startThread(new Thread(() -> {
			try {
				CryptoRSA rsa = new CryptoRSA();
				while(!serverSocket.isClosed()) {
					CardinalSocket socket = CardinalSocketFactory.createServerConnection(serverSocket.accept(), rsa);
				}
			}catch(IOException e) {
				Debug.log("IOException in server connector", Info.ERROR);
				e.printStackTrace();
			}
		}), SERVER_CONNECTOR);
	}
	
	public int getPort() {
		return port;
	}

}

