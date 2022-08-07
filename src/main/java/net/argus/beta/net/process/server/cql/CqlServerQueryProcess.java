package net.argus.beta.net.process.server.cql;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.Process;
import net.argus.beta.net.process.server.ServerProcessRegister;

public class CqlServerQueryProcess extends CqlServerProcess {
	
	public CqlServerQueryProcess(SSLSocket socket, ServerProcessRegister register) throws IOException {
		super("query", socket, register);
	}

	@Override
	protected void process(PackageReturn connectPackage) throws IOException {
		
	}

	@Override
	public Process create(SSLSocket socket) throws IOException {
		return new CqlServerQueryProcess(socket, getRegister());
	}

}
