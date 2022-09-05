package net.argus.beta.net.cql;

import java.io.IOException;

import net.argus.beta.net.ctp.plugin.CtpServerPlugin;
import net.argus.beta.net.process.server.ServerProcessRegister;
import net.argus.beta.net.process.server.cql.CqlServerQueryProcess;
import net.argus.beta.net.session.SessionTokenAuthority;
import net.argus.database.DataBase;

public class CqlServerPlugin implements CtpServerPlugin {
	
	private DataBase base;
	
	public CqlServerPlugin(DataBase base) {
		this.base = base;
	}

	@Override
	public void setDefault(ServerProcessRegister register) throws IOException {
		SessionTokenAuthority.createSessionTokenAuthority("cql");
		register.linkPathToProcess(new CqlServerQueryProcess(null, register));
	}
	
	public DataBase getBase() {
		return base;
	}

}
