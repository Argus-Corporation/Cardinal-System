package net.argus.beta.net.process.client;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.Stream;
import net.argus.beta.net.ctp.CtpURLConnection;
import net.argus.beta.net.pack.PackageReturn;

public class ClientStream extends Stream {
	
	private SwitchClientProcess switchClient;
	
	private SSLSocket sock;

	public ClientStream(CtpURLConnection connection, SwitchClientProcess switchClient) throws IOException {
		this(connection.getSocket(), switchClient);
	}
	
	public ClientStream(SSLSocket sock, SwitchClientProcess switchClient) throws IOException {
		super(sock);
		this.switchClient = switchClient;
		this.sock = sock;
	}
	
	@Override
	public PackageReturn nextPackage() throws IOException {
		return nextPackage(false, false);
	}
	
	public PackageReturn nextPackage(boolean returnFirstPack, boolean directReturn) throws IOException {
		PackageReturn ret = null;
		try {
			do {
				ret = super.nextPackage();
				if(directReturn)
					break;
			}while(switchClient.create(sock).startProcess(ret).isSuccess() && !returnFirstPack);
		}catch(NullPointerException e) {}

		return ret;
	}
	
	public SSLSocket getSocket() {
		return sock;
	}

}
