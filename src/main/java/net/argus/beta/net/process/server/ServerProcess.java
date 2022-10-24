package net.argus.beta.net.process.server;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.process.LocatedProcess;

public abstract class ServerProcess extends LocatedProcess {

	public ServerProcess(String path, SSLSocket socket, ServerProcessRegister register) throws IOException {
		super(path, socket, register);
	}
	
	@Override
	public ServerProcessRegister getRegister() {
		return (ServerProcessRegister) super.getRegister();
	}
	
	@Override
	public abstract ServerProcess create(SSLSocket socket) throws IOException;

}
