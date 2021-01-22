package net.argus.chat.client;

import net.argus.chat.client.gui.GUIClient;
import net.argus.system.UserSystem;

public class ClientConfig {
	
	public static int getDefultPort() {return GUIClient.config.getInt("port");}
	public static int getDefultPortCrypt() {return GUIClient.config.getInt("port.crypt");}
	
	public static native String getDefaultKey();
	
	static {
		UserSystem.loadLibrary("config");
	}

}
