package net.argus.beta.net.process.server;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.session.SessionTokenAuthority;
import net.argus.cjson.value.CJSONString;
import net.argus.cjson.value.CJSONValue;

public abstract class SecurityServerProcess extends ServerProcess {
	
	private SessionTokenAuthority authority;

	public SecurityServerProcess(String path, SSLSocket socket, ServerProcessRegister register, SessionTokenAuthority authority) throws IOException {
		super(path, socket, register);
		this.authority = authority;
	}

	@Override
	protected boolean process(PackageReturn connectPackage) throws IOException {
		CJSONValue sessionVal = null;
		if((sessionVal = connectPackage.getValue("session_token")) == null && !(sessionVal instanceof CJSONString))
			return false;

		if(authority == null)
			return false;
		
		if(authority.validSessionToken(sessionVal.getValue().toString()))
			return securityProcess(connectPackage);
		//else
		//	error
		
		return false;
	}
	
	protected abstract boolean securityProcess(PackageReturn pack) throws IOException;
	
	public SessionTokenAuthority getAuthority() {
		return authority;
	}


}
	