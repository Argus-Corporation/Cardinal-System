package net.argus.beta.net.process.server.cql;

import java.io.IOException;
import java.util.List;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.cql.CqlServerPlugin;
import net.argus.beta.net.ctp.plugin.CtpServerPlugin;
import net.argus.beta.net.pack.cql.CqlPackageDefault;
import net.argus.beta.net.process.server.SecurityServerProcess;
import net.argus.beta.net.process.server.ServerProcessRegister;
import net.argus.beta.net.session.SessionTokenAuthority;
import net.argus.database.DataBase;

public abstract class CqlServerProcess extends SecurityServerProcess {

	public static final String DEFAULT_PATH = CqlPackageDefault.DEFAULT_PATH;

	public CqlServerProcess(String path, SSLSocket socket, ServerProcessRegister register) throws IOException {
		super("/" + DEFAULT_PATH + "/" + path, socket, register, SessionTokenAuthority.getTokenAuthority("cql"));
	}
	
	public DataBase getLinkedBase() {
		List<CtpServerPlugin> plugs = getRegister().getParent().getPlugins();
		for(CtpServerPlugin p : plugs)
			if(p instanceof CqlServerPlugin)
				return ((CqlServerPlugin) p).getBase();
		return null;
	}
	
}
