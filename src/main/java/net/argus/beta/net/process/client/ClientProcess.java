package net.argus.beta.net.process.client;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.process.LocatedProcess;

public abstract class ClientProcess extends LocatedProcess {

	public ClientProcess(String path, SSLSocket socket, ClientProcessRegister register) throws IOException {
		super(path, socket, register);
	}
	
	@Override
	public ClientProcessRegister getRegister() {
		return (ClientProcessRegister) super.getRegister();
	}

}
