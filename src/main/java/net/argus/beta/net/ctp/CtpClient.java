package net.argus.beta.net.ctp;

import java.io.IOException;

import net.argus.beta.net.Ping;
import net.argus.beta.net.ctp.plugin.CtpClientPlugin;
import net.argus.beta.net.ctp.plugin.CtpClientServerNode;
import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.client.ClientProcessRegister;
import net.argus.beta.net.process.client.PongClientProcess;
import net.argus.beta.net.process.client.SwitchClientProcess;
import net.argus.beta.net.process.client.ctp.CtpErrorClientProcess;
import net.argus.util.Version;

public abstract class CtpClient extends CtpClientServerNode<ClientProcessRegister, CtpClientPlugin> {
	
	public static final int DEFAULT_PORT = CtpURLConnection.DEFAULT_CTP_PORT;
	
	public static final String DEFAULT_AUTHORITY = "ctp";
	
	private String host;
	private int port;
	
	private String authority;
	
	private CtpSender sender;
		
	public CtpClient(String host, int port, String authority, Version version) throws IOException {
		super(new ClientProcessRegister(), version);
		this.host = host;
		this.port = port;
		this.authority = authority;
		
		getRegister().linkPathToProcess(new PongClientProcess(null, getRegister()));
		getRegister().linkPathToProcess(new CtpErrorClientProcess(null, getRegister()));
		
		this.sender = createSender(host, port, authority, new SwitchClientProcess(null, getRegister()));
		if(sender == null)
			throw new NullPointerException("sender is null");
	}
	
	protected abstract CtpSender createSender(String host, int port, String authority, SwitchClientProcess switchClient);
	
	public void connect(String userName, String password) throws IOException {
		sender.connect(userName, password);
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}

	public String getAuthority() {
		return authority;
	}
	
	public CtpSender getSender() {
		return sender;
	}
	
	public Ping ping() throws IOException {
		return sender.ping();
	}
	
	public PackageReturn access(boolean returnFirstPack, String userName, String password, String path, String ... ctpQuerys) throws IOException {
		return sender.access(returnFirstPack, userName, password, path, ctpQuerys);
	}
	
	public PackageReturn access(String userName, String password, String path, String ... ctpQuerys) throws IOException {
		return sender.access(true, userName, password, path, ctpQuerys);
	}
	
	public PackageReturn accessWithToken(boolean returnFirstPack, String path, String ... ctpQuerys) throws IOException {
		return sender.accessWithToken(returnFirstPack, path, ctpQuerys);
	}
	
	public PackageReturn accessWithToken(String path, String ... ctpQuerys) throws IOException {
		return sender.accessWithToken(true, path, ctpQuerys);
	}
	
	public PackageReturn accessWithoutToken(boolean returnFirstPack, String path, String ... ctpQuerys) throws IOException {
		return sender.accessWithoutToken(returnFirstPack, path, ctpQuerys);
	}
	
	public PackageReturn accessWithoutToken(String path, String ... ctpQuerys) throws IOException {
		return sender.accessWithoutToken(true, path, ctpQuerys);
	}
	
}
