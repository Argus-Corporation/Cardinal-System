package net.argus.client.info;

import net.argus.util.ErrorCode;

public class Infos {
	
	public static final Info KICK = new Info(ErrorCode.kick, Info.INFORMATION_MESSAGE).registry();
	
}
