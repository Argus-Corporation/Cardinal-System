package net.argus.net;

import net.argus.util.Status;

public class StatusConnection extends Status {
	
	public StatusConnection(boolean connected, String arg) {
		super(connected, arg);
	}
	
	public boolean isConnected() {return getBoolean();}
	public String getArgument() {return getString();}

}
