package net.argus.beta.net.ctp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.Ping;
import net.argus.beta.net.pack.Package;
import net.argus.beta.net.pack.PackagePrefab;
import net.argus.beta.net.process.client.ClientStream;
import net.argus.beta.net.process.client.SwitchClientProcess;
import net.argus.beta.net.ssl.CardinalSSLSocketFactory;

public class CtpURLConnection extends URLConnection {
	
	public static final int DEFAULT_CTP_PORT = 11066;
		
	private SSLSocket socket;
	private ClientStream stream;
	
	private SwitchClientProcess switchClient; 

	protected CtpURLConnection(URL url) {
		super(url);
	}

	@Override
	public void connect() throws IOException {
		int port = url.getPort();
		if(port == -1)
			port = url.getDefaultPort();
		
		socket = CardinalSSLSocketFactory.getSocket(InetAddress.getByName(url.getHost()), port);
		stream = new ClientStream(socket, switchClient);
		
		Package connectPack = PackagePrefab.getDefaultPackageWithPath(url);
		if(connectPack != null)
			send(connectPack);
	}
	
	public Ping ping() throws IOException {
		stream.send(PackagePrefab.getPingPackage());
		return new Ping(stream.nextPackage(true, false));
	}
	
	@Override
	public OutputStream getOutputStream() throws IOException {
		if(socket == null)
			throw new UnknownServiceException("You must connect before !");
		
		return socket.getOutputStream();
	}
	
	@Override
	public InputStream getInputStream() throws IOException {
		if(socket == null)
			throw new UnknownServiceException("You must connect before !");
		
		return socket.getInputStream();
	}
	
	public SSLSocket getSocket() {
		return socket;
	}
	
	public void send(Package pack) {
		if(stream == null)
			return;
		stream.send(pack);
	}
	
	public void setSwitchClientProcess(SwitchClientProcess switchClient) {
		this.switchClient = switchClient;
	}
	
}