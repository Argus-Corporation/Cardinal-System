package net.argus.beta.net.process.server.ctp;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.ctp.CtpPackageDefault;
import net.argus.beta.net.process.server.ServerProcess;
import net.argus.beta.net.process.server.ServerProcessRegister;

public abstract class CtpServerProcess extends ServerProcess {
	
	public static final String DEFAULT_PATH = CtpPackageDefault.DEFAULT_PATH;

	public CtpServerProcess(String path, SSLSocket socket, ServerProcessRegister register) throws IOException {
		super("/" + DEFAULT_PATH + "/" + path, socket, register);
	}

}
