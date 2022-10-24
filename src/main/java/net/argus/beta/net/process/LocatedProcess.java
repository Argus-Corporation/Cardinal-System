package net.argus.beta.net.process;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

public abstract class LocatedProcess extends Process {
	
	private String path;

	public LocatedProcess(String path, SSLSocket socket, ProcessRegister register) throws IOException {
		super(socket, register);
		
		if(path == null)
			throw new NullPointerException();
		
		if(!path.startsWith("/"))
			path = "/" + path;
		
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	@Override
	public abstract LocatedProcess create(SSLSocket socket) throws IOException;

}
