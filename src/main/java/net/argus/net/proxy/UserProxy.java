package net.argus.net.proxy;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import net.argus.net.pack.key.PackageKey;
import net.argus.net.socket.Stream;

public class UserProxy {
	
	private Socket socket;
	
	private Stream stream;
	
	public UserProxy(Socket socket) {
		this.socket = socket;
		
		try {this.stream = new Stream(socket);}
		catch(IOException e) {e.printStackTrace();}
	}
	
	public Socket getSocket() {return socket;}
	
	public Stream getStream() {return stream;}
	
	public List<PackageKey> nextPackage() throws IOException {return stream.nextPackageKey();}
	public void send(Object pack) {stream.send(pack);}
	
	public void close() throws IOException {socket.close(); stream.close();}

}
