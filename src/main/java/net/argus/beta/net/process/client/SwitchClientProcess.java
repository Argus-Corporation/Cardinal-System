package net.argus.beta.net.process.client;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.process.Process;
import net.argus.beta.net.process.SwitchProcess;

public class SwitchClientProcess extends SwitchProcess {

	public SwitchClientProcess(SSLSocket socket, ClientProcessRegister register) throws IOException {
		super(socket, register);
	}

	@Override
	public Process create(SSLSocket socket) throws IOException {
		return new SwitchClientProcess(socket, (ClientProcessRegister) getRegister());
	}

}
