package net.argus.net.proxy;

import java.io.IOException;
import java.util.List;

import net.argus.net.pack.Package;
import net.argus.net.pack.key.PackageKey;
import net.argus.util.debug.Debug;

public class ProxyProcess extends Thread {
	
	private UserProxy in, out;
	
	public ProxyProcess(UserProxy in, UserProxy out) {
		this.in = in;
		this.out = out;

		setName("proxy-process");
	}
	
	@Override
	public void run() {
		try {
			while(!in.getSocket().isClosed() && !out.getSocket().isClosed()) {
				List<PackageKey> keys = in.nextPackage();
				
				if(keys.size() == 0) {
					close();
					break;
				}
				
				Package pack = new Package(keys);
				out.send(pack);
				
			}
		}catch(IOException e) {Debug.log("Connection was closed");}
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
	}

}
