package net.argus.net.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import net.argus.net.pack.Package;
import net.argus.net.pack.key.PackageKey;

public class WebSocket extends CardinalSocket {
	
	public WebSocket() {
		super();
	}
	
	public WebSocket(Socket socket) throws IOException {
		super(socket);
	}

	@Override
	protected void setSocket(Socket socket) throws IOException {
		super.setSocket(socket);
		stream = new WebStream(socket);
	}
	
	@Override
	public void connect(String host, int port) throws UnknownHostException, IOException {
		setSocket(new Socket(host, port));
	}

	@Override
	public Object[] getInfos() {
		return null;
	}

	@Override
	public void setInfos(Object[] infos) {}

	@Override
	public Package nextPackage() throws IOException {
		List<PackageKey> lines = stream.nextPackageKey();
		if(lines == null) {
			close("web close");
			return null;
		}
		
		return new Package(lines);
	}
/*
	@Override
	public void send(Package pack) throws IOException {
		stream.send(pack);
	}
*/
	@Override
	public CardinalSocket create() throws IOException {
		return new WebSocket();
	}

	@Override
	public CardinalSocket create(Socket socket) throws IOException {
		return new WebSocket(socket);
	}

	@Override
	public void send(Object pack) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
