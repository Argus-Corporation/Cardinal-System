package net.argus.beta.net.process.client.ctp;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.ctp.CtpPackageDefault;
import net.argus.beta.net.process.client.ClientProcess;
import net.argus.beta.net.process.client.ClientProcessRegister;

public abstract class CtpClientProcess extends ClientProcess {
	
	public static final String DEFAULT_PATH = CtpPackageDefault.DEFAULT_PATH;

	public CtpClientProcess(String path, SSLSocket socket, ClientProcessRegister register) throws IOException {
		super("/" + DEFAULT_PATH + "/" + path, socket, register);
	}
	
	@Override
	public abstract CtpClientProcess create(SSLSocket socket) throws IOException;

}
