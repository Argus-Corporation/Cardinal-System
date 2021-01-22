package net.argus.server.command;

import net.argus.server.command.structure.Structures;

public class Commands {
	
	public static final Command HELP = new HelpCommand("help", 0).registry();
	
	public static final Command STOP = new StopCommand("stop", 10).registry();
	public static final Command KICK = new KickCommand("kick", 5).setStructure(Structures.TARGET).registry();
	public static final Command BAN = new BanCommand("ban", 10).setStructure(Structures.TARGET).registry();
	public static final Command UNBAN = new UnBanCommand("unban", 10).setStructure(Structures.IP).registry();
	public static final Command PASSWORD = new PasswordCommand("password", 0).setStructure(Structures.PASSWORD).registry();
	public static final Command NICK = new NickCommand("nick", 5).setStructure(Structures.NEW_VALUE).registry();
	public static final Command NOTIFY = new NotifyCommand("notify", 0).setStructure(Structures.TARGET).registry();
	public static final Command LIST = new ListCommand("list", 0).registry();
	public static final Command MP = new MPCommand("mp", 0).setStructure(Structures.MESSAGE).registry();
	public static final Command LEAVE = new LeaveCommande("leave", 0).registry();
	
}
