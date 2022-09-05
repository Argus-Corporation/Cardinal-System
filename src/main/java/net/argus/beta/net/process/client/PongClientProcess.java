package net.argus.beta.net.process.client;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.Ping;
import net.argus.beta.net.pack.PackageReturn;
import net.argus.util.debug.Debug;

public class PongClientProcess extends ClientProcess {

	public PongClientProcess(SSLSocket socket, ClientProcessRegister register) throws IOException {
		super("pong", socket, register);
	}

	@Override
	protected boolean process(PackageReturn connectPackage) throws IOException {
		int ping = connectPackage.getInteger("ping");
		Debug.log("Ping Pong successful in " + Ping.toStringPing(ping));
		return true;
	}

	@Override
	public PongClientProcess create(SSLSocket socket) throws IOException {
		return new PongClientProcess(socket, getRegister());
	}

}
