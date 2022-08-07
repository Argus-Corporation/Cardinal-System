package net.argus.beta.net.ctp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

import net.argus.beta.net.process.server.ServerProcessRegister;
import net.argus.beta.net.process.server.SwitchServerProcess;
import net.argus.beta.net.process.server.ctp.CtpServerRequestProcess;
import net.argus.beta.net.ssl.CardinalSSLSocketFactory;

public class CtpServer {
	
	private List<CtpServerPlugin> plugins = new ArrayList<CtpServerPlugin>();
	
	private int port;
	private boolean closed;
	
	private SSLServerSocket server;
	
	private ServerProcessRegister register;
	
	public CtpServer() throws IOException {
		this(CtpURLConnection.DEFAULT_CTP_PORT);
	}
	
	public CtpServer(int port) throws IOException {
		this.port = port;
		server = CardinalSSLSocketFactory.getServerSocket(port);
		register = new ServerProcessRegister();
		
		register.linkPathToProcess(new CtpServerRequestProcess(null, register));
	}
	
	public void addPlugin(CtpServerPlugin plugin) throws IOException {
		if(!plugins.contains(plugin))
			plugin.setDefault(register);
	}
	
	public List<CtpServerPlugin> getPlugins() {return plugins;}
	
	public void open() throws IOException {
		while(!closed) {
			new SwitchServerProcess((SSLSocket) server.accept(), register).startThreadProcess();
		}
	}
	
	public void close() {
		closed = true;
	}
	
	public int getPort() {
		return port;
	}

}
