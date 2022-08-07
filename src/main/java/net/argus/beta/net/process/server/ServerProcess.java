package net.argus.beta.net.process.server;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.process.Process;

public abstract class ServerProcess extends Process {
	
	private String path;

	public ServerProcess(String path, SSLSocket socket, ServerProcessRegister register) throws IOException {
		super(socket, register);
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}

}
