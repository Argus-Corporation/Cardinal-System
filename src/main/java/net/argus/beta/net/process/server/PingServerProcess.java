package net.argus.beta.net.process.server;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.PackagePrefab;
import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.ProcessReturn;

public class PingServerProcess extends ServerProcess {

	public PingServerProcess(SSLSocket socket, ServerProcessRegister register) throws IOException {
		super("ping", socket, register);
	}

	@Override
	protected ProcessReturn process(PackageReturn connectPackage) throws IOException {
		String start = connectPackage.getString("start_time");
		if(start == null)
			return new ProcessReturn(false, "start_time is not defined");
		
		long sTime = Long.valueOf(start);
		int ping = (int) ((int) System.currentTimeMillis() - sTime);
		if(ping > 999)
			ping = 1000;
		
		send(PackagePrefab.getPongPackage(ping));
		close();
		return new ProcessReturn(true);
	}

	@Override
	public PingServerProcess create(SSLSocket socket) throws IOException {
		return new PingServerProcess(socket, (ServerProcessRegister) getRegister());
	}

}