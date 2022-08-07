package net.argus.beta.net.cql;

import java.io.IOException;
import java.net.URL;

import net.argus.beta.net.Stream;
import net.argus.beta.net.ctp.CtpURLConnection;
import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.session.SessionToken;

public class CqlSender {
	
	public static final String CQL_AUTHORITY = "cql";
	
	private String host;
	private int port;
	
	private SessionToken token;
	
	public CqlSender(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect(String userName, String password) throws IOException {
		Stream stream = getStream(userName, password, host, port, "/ctp/request", "authority", CQL_AUTHORITY);
		if(stream == null)
			return;
		
		PackageReturn ret = stream.nextPackage();
		SessionToken token = new SessionToken(userName, ret.getString("session_token"));
		this.token = token;
	}
	
	public void sendQuery(String query) throws IOException {
		if(token == null)
			return;
		
		Stream stream = getStream(token.getSessionToken(), null, host, port, "/cql/query", "query", query);
		if(stream == null)
			return;
		
		System.out.println(stream.nextPackage());
	}
	
	public void setToken(SessionToken token) {
		this.token = token;
	}
	
	private static CtpURLConnection getConnection(String userName, String password, String host, int port, String path, String ... ctpQuerys) throws IOException {
		String u = "ctp://";
		
		if(userName != null && !userName.equals("")) {
			u += userName;
			if(password != null && !password.equals(""))
				u += ":" + password;
			
			u += "@";
		}
		
		if(host == null || host.equals(""))
			return null;
		
		u += host;
		
		if(port < 1)
			port = CqlClient.DEFAULT_PORT;
		
		if(port != CqlClient.DEFAULT_PORT)
			u += ":" + port;
		
		
		if(path != null && !path.equals(""))
			u += path;
		else
			u += "/";
		
		if(ctpQuerys != null && ctpQuerys.length > 0 && (ctpQuerys.length % 2) == 0) {
			u += "?";
			for(int i = 0; i < ctpQuerys.length; i += 2)
				u +=  ctpQuerys[i] + "=" + ctpQuerys[i + 1] + "&";
			u = u.substring(0, u.length() - 1);
		}

		URL url = new URL(u);
		return (CtpURLConnection) url.openConnection();
		
	}
	
	private static Stream getStream(String userName, String password, String host, int port, String path, String ... ctpQuerys) throws IOException {
		CtpURLConnection con = getConnection(userName, password, host, port, path, ctpQuerys);
		if(con == null)
			return null;
		
		con.connect();
		return new Stream(con);
	}
	
}
