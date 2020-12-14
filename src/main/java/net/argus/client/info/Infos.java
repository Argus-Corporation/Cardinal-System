package net.argus.client.info;

public class Infos {
	
	public static final Info VERSION = new Info("version", "Obsolete version", 0x12345, Info.ERROR_MESSAGE).registry();
	public static final Info CORRUPT_VERSION = new Info("version", "Corrupt version", 0x45973, Info.ERROR_MESSAGE).registry();
	public static final Info ERROR = new Info("error", "Error", 0x000000, Info.ERROR_MESSAGE).registry();

}
