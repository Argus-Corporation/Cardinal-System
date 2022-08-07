package net.argus.beta.net.process.server.cql;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.cql.CqlPackageDefault;
import net.argus.beta.net.process.server.ServerProcess;
import net.argus.beta.net.process.server.ServerProcessRegister;

public abstract class CqlServerProcess extends ServerProcess {

	public static final String DEFAULT_PATH = CqlPackageDefault.DEFAULT_PATH;

	public CqlServerProcess(String path, SSLSocket socket, ServerProcessRegister register) throws IOException {
		super("/" + DEFAULT_PATH + "/" + path, socket, register);
	}
	
}
