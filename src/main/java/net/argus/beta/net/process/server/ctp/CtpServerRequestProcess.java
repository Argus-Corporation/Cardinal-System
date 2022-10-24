package net.argus.beta.net.process.server.ctp;

import java.io.IOException;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.PackagePrefab;
import net.argus.beta.net.pack.PackageReturn;
import net.argus.beta.net.process.ProcessReturn;
import net.argus.beta.net.process.server.ServerProcessRegister;
import net.argus.beta.net.session.SessionToken;
import net.argus.beta.net.session.SessionTokenAuthority;
import net.argus.cjson.value.CJSONString;

public class CtpServerRequestProcess extends CtpServerProcess {
	
	public CtpServerRequestProcess(SSLSocket socket, ServerProcessRegister register) throws IOException {
		super("request", socket, register);
	}
	
	@Override
	protected ProcessReturn process(PackageReturn connectPackage) throws IOException {
		if(!(connectPackage.getValue("user_name") instanceof CJSONString))
			return new ProcessReturn(false, "user_name is not defined");
		if(!(connectPackage.getValue("password") instanceof CJSONString))
			return new ProcessReturn(false, "password is not defined");
		if(!(connectPackage.getValue("authority") instanceof CJSONString))
			return new ProcessReturn(false, "authority is not defined");
		
		String userName = connectPackage.getString("user_name");
		String password = connectPackage.getString("password");
		String authority = connectPackage.getString("authority");
				
		SessionTokenAuthority tokenAuthority = SessionTokenAuthority.getTokenAuthority(authority);
		if(tokenAuthority == null)
			return new ProcessReturn(false, "authority \"" + authority + "\" not found");

		SessionToken token = SessionToken.genSessionToken(userName, password);
		tokenAuthority.addToken(token);
		
		send(PackagePrefab.getSessionTokenPackage(token));
		
		close();
		return new ProcessReturn(true);
	}

	@Override
	public CtpServerRequestProcess create(SSLSocket socket) throws IOException {
		return new CtpServerRequestProcess(socket, getRegister());
	}
	
}
