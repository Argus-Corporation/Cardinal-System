package net.argus.net.server.command;

public class Commands {
	
	public static final Command STOP = new StopCommand();
	public static final Command CLOSE = new CloseCommand();
	
	/**--Deactivate--**/
	/*public static final Command BAN = new BanCommand();
	public static final Command UNBAN = new UnbanCommand();*/
	
	public static final Command KICK = new KickCommand();
	
	public static final Command LIST = new ListCommand();
		
	public static final Command ROOM = new RoomCommand();
	public static final Command LIST_ROOM = new ListRoomCommand();
	public static final Command CREATE_ROOM = new CreateRoomCommand();
	public static final Command JOIN_ROOM = new JoinRoomCommand();
	
	public static final Command ROLE = new RoleCommand();
	public static final Command LIST_ROLE = new ListRoleCommand();
	
	public static final Command USER_INFO = new UserInfoCommand();
	
	public static final Command COMMAND = new CommandCommand();
	
	public static final Command LEAVE = new LeaveCommand();
	public static final Command HELP = new HelpCommand();
	
}
