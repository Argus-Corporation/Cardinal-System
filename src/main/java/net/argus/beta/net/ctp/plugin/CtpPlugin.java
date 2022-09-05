package net.argus.beta.net.ctp.plugin;

import java.io.IOException;

import net.argus.beta.net.process.ProcessRegister;

public interface CtpPlugin <R extends ProcessRegister> {
	
	public void setDefault(R register) throws IOException;


}
