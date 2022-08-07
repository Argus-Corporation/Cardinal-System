package net.argus.beta.net.ctp;

import java.io.IOException;

import net.argus.beta.net.process.server.ServerProcessRegister;

public interface CtpServerPlugin {
	
	public abstract void setDefault(ServerProcessRegister register) throws IOException;

}
