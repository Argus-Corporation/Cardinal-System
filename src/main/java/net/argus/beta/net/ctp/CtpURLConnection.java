package net.argus.beta.net.ctp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.Stream;
import net.argus.beta.net.pack.Package;
import net.argus.beta.net.ssl.CardinalSSLSocketFactory;

public class CtpURLConnection extends URLConnection {
	
	public static final int DEFAULT_CTP_PORT = 11066;
		
	private SSLSocket socket;
	private Stream stream;
	private Package connectPack;

	protected CtpURLConnection(URL url, Package connectPack) {
		super(url);
		this.connectPack = connectPack;
	}

	@Override
	public void connect() throws IOException {
		int port = url.getPort();
		if(port == -1)
			port = url.getDefaultPort();
		
		socket = CardinalSSLSocketFactory.getSocket(InetAddress.getByName(url.getHost()), port);
		stream = new Stream(socket);

		if(connectPack != null)
			send(connectPack);
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
	
}