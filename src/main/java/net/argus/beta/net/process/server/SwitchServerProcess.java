package net.argus.beta.net.process.server;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.process.Process;
import net.argus.beta.net.process.SwitchProcess;

public class SwitchServerProcess extends SwitchProcess {
	

	public SwitchServerProcess(SSLSocket socket, ServerProcessRegister register) throws IOException {
		super(socket, register);
	}

	@Override
	public Process create(SSLSocket socket) throws IOException {
		return new SwitchServerProcess(socket, (ServerProcessRegister) getRegister());
	}
	/*
	static {
		try {
			ServerProcessHandler.linkPathToProcess(new CtpServerRequestProcess(null));
			ServerProcessHandler.linkPathToProcess(new CqlServerQueryProcess(null));
		}catch(IOException e) {e.printStackTrace();}
	
	}*/

}
	