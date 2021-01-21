package net.argus.chat.client;

import net.argus.system.UserSystem;

public class ClientConfig {
	
	public static int DEFAULT_PORT = 11066;
	public static int DEFAULT_CRYPT_PORT = 11067;
	
	public static native String getDefaultKey();
	
	static {
		UserSystem.loadLibrary("config");
	}

}
