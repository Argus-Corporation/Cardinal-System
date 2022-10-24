package net.argus.beta.net.ctp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.argus.beta.net.Ping;
import net.argus.beta.net.cql.CqlClient;
import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.client.ClientStream;
import net.argus.beta.net.process.client.SwitchClientProcess;
import net.argus.beta.net.session.SessionToken;

public class CtpSender {
		
	private String host;
	private int port;
	
	private String authority;
	
	private SessionToken token;
	
	private SwitchClientProcess switchClient;
	
	public CtpSender(String host, int port, String authority, SwitchClientProcess switchClient) {
		this.host = host;
		this.port = port;
		this.authority = authority;
		this.switchClient = switchClient;
	}

	public void connect(String userName, String password) throws IOException {
		ClientStream stream = getStream(userName, password, "/ctp/request", "authority", authority);
		if(stream == null)
			return;
		
		PackageReturn ret = stream.nextPackage();
		SessionToken token = new SessionToken(userName, ret.getString("session_token"));
		this.token = token;
	}
	
	public PackageReturn access(boolean returnFirstPack, String userName, String password, String path, String ... ctpQuerys) throws IOException {
		ClientStream stream = getStream(userName, password, path, ctpQuerys);
		
		return stream.nextPackage(returnFirstPack, false);
	}
	
	public PackageReturn accessWithToken(boolean returnFirstPack, String path, String ... ctpQuerys) throws IOException {
		return access(returnFirstPack, getToken().getSessionToken(), null, path, ctpQuerys);
	}
	
	public PackageReturn accessWithoutToken(boolean returnFirstPack, String path, String ... ctpQuerys) throws IOException {
		return access(returnFirstPack, null, null, path, ctpQuerys);
	}
	
	public static CtpURLConnection getConnection(String userName, String password, String host, int port, String path, String ... ctpQuerys) throws IOException {
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
				if(ctpQuerys[i] != null && ctpQuerys[i + 1] != null)
					u +=  ctpQuerys[i] + "=" + ctpQuerys[i + 1] + "&";
			u = u.substring(0, u.length() - 1);
		}

		URL url = new URL(u);
		
		return (CtpURLConnection) url.openConnection();
		
	}
	
	public Ping ping() throws IOException {
		URL pingUrl = getPingURL();
		CtpURLConnection con = (CtpURLConnection) pingUrl.openConnection();
		con.setSwitchClientProcess(switchClient);
		con.connect();
		
		ClientStream str = new ClientStream(con, switchClient);
		return new Ping(str.nextPackage(true, false));
	}
	
	public URL getPingURL() throws MalformedURLException {
		return new URL("ctp://" + host + ":" + port + "/ping");
	}
	
	protected ClientStream getStream(String userName, String password, String path, String ... ctpQuerys) throws IOException {
		return getStream(switchClient, userName, password, host, port, path, ctpQuerys);
	}
	
	public static ClientStream getStream(SwitchClientProcess switchClient, String userName, String password, String host, int port, String path, String ... ctpQuerys) throws IOException {
		CtpURLConnection con = getConnection(userName, password, host, port, path, ctpQuerys);
		if(con == null)
			return null;
		con.setSwitchClientProcess(switchClient);
		con.connect();
		return new ClientStream(con, switchClient);
	}
	
	public String getAuthority() {
		return authority;
	}
	
	public String getHost() {
		return host;
	}
	
	public int getPort() {
		return port;
	}
	
	public SessionToken getToken() {
		return token;
	}
	
	public SwitchClientProcess getSwitchClient() {
		return switchClient;
	}
	
	protected void setToken(SessionToken token) {
		this.token = token;
	}

}
