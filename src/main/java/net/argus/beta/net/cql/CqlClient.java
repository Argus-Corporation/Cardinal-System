package net.argus.beta.net.cql;

import java.io.IOException;

import net.argus.beta.net.ctp.CtpClient;
import net.argus.beta.net.process.client.SwitchClientProcess;
import net.argus.util.Version;

public class CqlClient extends CtpClient {
	
	public static final String CQL_AUTHORITY = "cql";
	
	public CqlClient(String host, Version version) throws IOException {
		super(host, DEFAULT_PORT, CQL_AUTHORITY, version);
	}
	
	public CqlClient(String host, int port, Version version) throws IOException {
		super(host, port, CQL_AUTHORITY, version);
	}

	@Override
	protected CqlSender createSender(String host, int port, String authority, SwitchClientProcess switchClient) {
		return new CqlSender(host, port, authority, switchClient);
	}
	
	public String query(String query) throws IOException {
		return getSender().sendQuery(query);
	}
	
	@Override
	public CqlSender getSender() {
		return (CqlSender) super.getSender();
	}

}
