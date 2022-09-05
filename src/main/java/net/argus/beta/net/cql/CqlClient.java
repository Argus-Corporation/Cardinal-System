package net.argus.beta.net.cql;

import java.io.IOException;

import net.argus.beta.net.ctp.CtpClient;
import net.argus.beta.net.process.client.SwitchClientProcess;

public class CqlClient extends CtpClient {
	
	public static final String CQL_AUTHORITY = "cql";
	
	public CqlClient(String host) throws IOException {
		super(host, DEFAULT_PORT, CQL_AUTHORITY);
	}
	
	public CqlClient(String host, int port) throws IOException {
		super(host, port, CQL_AUTHORITY);
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
