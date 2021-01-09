package net.argus.server.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.exception.SecurityException;
import net.argus.server.ServerSocketClient;
import net.argus.server.command.structure.Structure;
import net.argus.util.ArrayManager;
import net.argus.util.StringManager;
import net.argus.util.debug.Debug;

public abstract class Command {
	
	private static List<Command> coms = new ArrayList<Command>();
	
	protected CommandListener listener;
	
	protected Structure structure = new Structure();
	
	protected boolean activate;
	protected String commandName;
	protected int rank;
	
	public Command(String commandName, int rank) {
		this.commandName = "/" + commandName.toUpperCase();
		this.rank = rank;
		this.activate = true;
	}
	
	public void execut(String[] com, ServerSocketClient client) throws SecurityException, IOException {
		com = StringManager.valueOf(com);
		if(activate) {
			if(client.getRole().isValidExecuteCommand(this)) {
				if(ArrayManager.isExist(com, structure.getSize() - 1)) {
					if(listener != null) {
						listener.preExecute();
						listener.execute(com, client);
						listener.postExecute();
					}else 
						run(com, client);
				}else {
					client.getProcessServer().sendMessage("Command structure: " + structure.getStucture());
					Debug.log("Error: command structure is wrong " + com[0]);
				}
			}else {
				client.getProcessServer().sendMessage("You are not allowed to execute this command");
				Debug.log("Error: " + client.getPseudo() + " is not allowed to execute this command");
			}
		}else {
			client.getProcessServer().sendMessage("This command is not activated");
			Debug.log("Error: this command is not activated");
		}
	}
	
	protected void run(String[] com, ServerSocketClient Client) throws IOException, SecurityException{
		Debug.log("Error: execute command not found");
	}
	
	public static Command getCommand(String commandName) {
		commandName = commandName.toUpperCase();
		for(int i = 0; i < coms.size(); i++)
			if(coms.get(i).getCommandName().equals(commandName))
				return coms.get(i);
		
		Debug.log("Command not found");
		return null;
	}
	
	public CommandListener getCommandListener() {return listener;}
	public String getCommandName() {return commandName;}
	public boolean isActivate() {return activate;}
	public int getRank() {return rank;}
	
	
	public Command setRank(int rank) {this.rank = rank; return this;}
	public Command setActive(boolean activate) {this.activate = activate; return this;}
	public Command setStructure(Structure structure) {this.structure = structure; return this;}
	public Command addCommandListener(CommandListener listener) {this.listener = listener; return this;}
	
	public Command registry() {coms.add(this); return this;}

}
