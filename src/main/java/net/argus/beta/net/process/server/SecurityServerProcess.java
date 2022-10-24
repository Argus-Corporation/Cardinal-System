package net.argus.beta.net.process.server;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.ProcessReturn;
import net.argus.beta.net.session.SessionToken;
import net.argus.beta.net.session.SessionTokenAuthority;
import net.argus.cjson.value.CJSONString;
import net.argus.cjson.value.CJSONValue;

public abstract class SecurityServerProcess extends ServerProcess {
	
	private SessionTokenAuthority authority;
	
	private SessionToken token;

	public SecurityServerProcess(String path, SSLSocket socket, ServerProcessRegister register, SessionTokenAuthority authority) throws IOException {
		super(path, socket, register);
		this.authority = authority;
	}

	@Override
	protected ProcessReturn process(PackageReturn connectPackage) throws IOException {
		CJSONValue sessionVal = null;
		if((sessionVal = connectPackage.getValue("session_token")) == null && !(sessionVal instanceof CJSONString))
			return new ProcessReturn(false, "session_token is not defined or is not a string");

		if(authority == null)
			return new ProcessReturn(false, "authority is null");;
		
		if(authority.validSessionToken(sessionVal.getValue().toString())) {
			token = authority.getSessionToken(sessionVal.getValue().toString());
			return securityProcess(connectPackage);
		}
		
		return new ProcessReturn(false, "invalid session_token");
	}
	
	protected abstract ProcessReturn securityProcess(PackageReturn pack) throws IOException;
	
	public SessionTokenAuthority getAuthority() {
		return authority;
	}
	
	public SessionToken getToken() {
		return token;
	}

}
	