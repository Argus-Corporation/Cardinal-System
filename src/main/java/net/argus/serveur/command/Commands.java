package net.argus.serveur.command;

import net.argus.serveur.command.structure.Structures;

public class Commands {
	
	public static final Command STOP = new StopCommand("stop", 10).registry();
	public static final Command KICK = new KickCommand("kick", 5).setStructure(Structures.CPA).registry();
	public static final Command KICKALL = new KickAllCommand("kickall", 5).registry();
	public static final Command BAN = new BanCommand("ban", 10).setStructure(Structures.CPA).registry();
	public static final Command UNBAN = new UnBanCommand("unban", 10).setStructure(Structures.IP).registry();
	public static final Command LIST = new ListCommand("list", 0).registry();
	public static final Command PASSWORD = new PasswordCommand("password", 0).setStructure(Structures.PASSWORD).registry();
	
}
