package net.argus.beta.net.cql;

import java.io.IOException;

import net.argus.beta.net.Stream;
import net.argus.beta.net.ctp.CtpSender;
import net.argus.beta.net.process.client.SwitchClientProcess;

public class CqlSender extends CtpSender {

	
	public CqlSender(String host, int port, String authority, SwitchClientProcess switchClient) {
		super(host, port, authority, switchClient);
	}
	
	
	public String sendQuery(String query) throws IOException {
		if(getToken() == null)
			return null;
		
		Stream stream = getStream(getToken().getSessionToken(), null, "/cql/query", "query", query);
		if(stream == null)
			return null;
		
		return stream.nextPackage().getString("result");
	}
	
}
