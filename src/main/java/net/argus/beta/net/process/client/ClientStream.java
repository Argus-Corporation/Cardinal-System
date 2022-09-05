package net.argus.beta.net.process.client;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.Stream;
import net.argus.beta.net.ctp.CtpURLConnection;
import net.argus.beta.net.pack.PackageReturn;

public class ClientStream extends Stream {
	
	private SwitchClientProcess switchClient;

	public ClientStream(CtpURLConnection connection, SwitchClientProcess switchClient) throws IOException {
		super(connection.getInputStream(), connection.getOutputStream());
		this.switchClient = switchClient;
	}
	
	public ClientStream(SSLSocket sock) throws IOException {
		super(sock);
	}
	
	@Override
	public PackageReturn nextPackage() throws IOException {
		return nextPackage(false);
	}
	
	public PackageReturn nextPackage(boolean returnFirstPack) throws IOException {
		PackageReturn ret = null;
		do
			ret = super.nextPackage();
		while(switchClient.startProcess(ret) && !returnFirstPack);

		return ret;
	}

}
