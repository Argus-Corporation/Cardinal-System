package net.argus.beta.net.process.client.ctp;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.ProcessReturn;
import net.argus.beta.net.process.client.ClientProcessRegister;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class CtpErrorClientProcess extends CtpClientProcess {

	public CtpErrorClientProcess(SSLSocket socket, ClientProcessRegister register) throws IOException {
		super("error", socket, register);
	}

	@Override
	protected ProcessReturn process(PackageReturn connectPackage) throws IOException {
		String message = (String) connectPackage.getValue("message").getValue();
		Debug.log(message, Info.ERROR);
		return new ProcessReturn(true, message);
	}

	@Override
	public CtpErrorClientProcess create(SSLSocket socket) throws IOException {
		return new CtpErrorClientProcess(socket, getRegister());
	}

}
