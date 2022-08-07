package net.argus.beta.net.ctp;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import net.argus.beta.net.pack.PackagePrefab;

public class CtpURLStreamHandler extends URLStreamHandler {

	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		return new CtpURLConnection(u, PackagePrefab.getDefaultPackageWithPath(u));
	}
	
	@Override
	protected int getDefaultPort() {
		return CtpURLConnection.DEFAULT_CTP_PORT;
	}

}
