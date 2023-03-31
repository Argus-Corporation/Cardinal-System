package net.argus.beta.com.process;

import net.argus.util.Status;

public class ConnectionStatus extends Status {
	
	public ConnectionStatus(boolean connect, String arg) {
		super(connect, arg);
	}
	
	public boolean isAccepted() {
		return getBoolean();
	}
	
	public String getArgument() {
		return getString();
	}

}
