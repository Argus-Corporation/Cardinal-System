package net.argus.beta.net.ctp;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

import net.argus.beta.net.ctp.plugin.CtpClientServerNode;
import net.argus.beta.net.ctp.plugin.CtpServerPlugin;
import net.argus.beta.net.process.server.PingServerProcess;
import net.argus.beta.net.process.server.ServerProcessRegister;
import net.argus.beta.net.process.server.SwitchServerProcess;
import net.argus.beta.net.process.server.ctp.CtpServerRequestProcess;
import net.argus.beta.net.ssl.CardinalSSLSocketFactory;
import net.argus.util.Version;

public class CtpServer extends CtpClientServerNode<ServerProcessRegister, CtpServerPlugin> {
	
	private int port;
	private boolean closed;
	
	private SSLServerSocket server;
	
	public CtpServer(Version version) throws IOException {
		this(CtpURLConnection.DEFAULT_CTP_PORT, version);
	}
	
	public CtpServer(int port, Version version) throws IOException {
		super(new ServerProcessRegister(), version);
		this.port = port;
		server = CardinalSSLSocketFactory.getServerSocket(port);
		
		getRegister().setParent(this);
		getRegister().linkPathToProcess(new CtpServerRequestProcess(null, getRegister()));
		getRegister().linkPathToProcess(new PingServerProcess(null, getRegister()));
	}
	
	
	public void open() throws IOException {
		while(!closed) {
			new SwitchServerProcess((SSLSocket) server.accept(), getRegister()).startThreadProcess(null);
		}
	}
	
	public void close() {
		closed = true;
	}
	
	public int getPort() {
		return port;
	}

}
