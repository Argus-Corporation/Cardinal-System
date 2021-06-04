package net.argus.net.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import net.argus.net.pack.Package;

public class DefaultSocket extends CardinalSocket {
	
	public DefaultSocket() {}
	public DefaultSocket(Socket socket) throws IOException {super(socket);}

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
		return new Package(stream.nextPackage());
	}

	@Override
	public void send(Package pack) throws IOException {
		stream.send(pack);
	}

	@Override
	public CardinalSocket create() throws IOException {
		return new DefaultSocket();
	}

	@Override
	public CardinalSocket create(Socket socket) throws IOException {
		return new DefaultSocket(socket);
	}

}
