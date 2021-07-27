package net.argus.net.proxy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import net.argus.net.pack.PackagePrefab;
import net.argus.system.Network;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

/**
 * @author Django
 *
 */
public class ProxyServer extends Thread {
	
	private ServerSocket server;
	
	private InetAddress outputHost;
	private int outputPort;
	
	private boolean running;
	
	public ProxyServer(InetAddress outputHost, int outputPort, int port) throws IOException {
		server = new ServerSocket(port);
		
		this.outputHost = outputHost;
		this.outputPort = outputPort;
		
		setName("proxy-server");
	}
	
	@Override
	public synchronized void start() {
		running = true;
		super.start();
	}
	
	public void close() throws IOException {
		running = false;
		server.close();
	}
	
	@Override
	public void run() {
		Debug.log("Proxy address: " + Network.getLocalHost());
		Debug.log("Proxy port: " + server.getLocalPort());
		Debug.log("Proxy opened");
		
		while(running) {
			UserProxy user = null;
			try {
				user = new UserProxy(server.accept());
				UserProxy outUser = getOutputUserProxy();
				
				new ProxyProcess(user, outUser).start();
				new ProxyProcess(outUser, user).start();
				
				Debug.log("New user connected");
			}catch(IOException e) {
				Debug.log("Server not available", Info.ERROR);
				
				if(user != null) {
					user.send(PackagePrefab.genLogOutPackage("proxy"));
					
					try {user.close();}
					catch(IOException e1) {e1.printStackTrace();}
				}
			}
		}
	}
	
	private UserProxy getOutputUserProxy() throws IOException {
		return new UserProxy(new Socket(outputHost, outputPort));
	}

}
