package net.argus.net.server.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.PackagePrefab;
import net.argus.net.server.ServerProcess;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.net.server.role.Role;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public abstract class Command {
	
	private static List<Command> coms = new ArrayList<Command>();
	
	private boolean activate = true;
	private int rank = 0;
	
	private Structure structure;
	
	private String name;
	
	public Command(String name, Structure structure, int rank) {
		this.structure = structure;
		this.name = name;
		this.rank = rank;
		coms.add(this);
	}
	
	public Command(String name, Structure structure) {
		this(name, structure, 0);
	}
	
	public Command(String name) {
		this(name, new Structure());
	}
	
	
	public static Command getCommand(String name) {
		if(name.startsWith("/"))
			name = name.substring(1);
		
		name = name.split(" ")[0];
		
		for(Command com : coms)
			if(com.name.toUpperCase().equals(name.toUpperCase()))
				return com;
		
		return null;
	}
	
	public static List<Command> getCommands() {return coms;}
	
	public static List<Command> getCommands(Role role) {
		List<Command> com = new ArrayList<Command>();
		
		for(Command c : coms)
			if(role.isAllowed(c))
				com.add(c);

		return com;
	}
	
	public void execut(String com, ServerProcess process) throws IOException {
		if(!isActivate()) {
			Debug.log("Error: command not activated", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("Command \"/" + name + "\" is not activated"));
			return;
		}
		
		if(!process.getCardinalSocket().getProfile().getRole().isAllowed(this)) {
			Debug.log("Error: user " + process.getCardinalSocket().getProfile().getName() + " is not allowed to execute this command", Info.ERROR);
			process.send(PackagePrefab.genInfoPackage("You are not allowed to execute command \"/" + name + "\""));
			return;
		}
			
		StructuredCommand struc = CommandParser.structureCommand(com, structure);
		if(struc != null) {
			run(struc, process);
		}else {
			Debug.log("Error: this command structure is wrong " + structure, Info.ERROR);
			process.send(PackagePrefab.genInfoPackage(new String[] {"The command structure is wrong", structure.toString()}));
			return;
		}
	}
	
	protected abstract void run(StructuredCommand com, ServerProcess process) throws IOException;
	
	public Command setStructure(Structure structure) {this.structure = structure; return this;}
	public Structure getStructure() {return structure;}
	
	public Command setActivate(boolean activate) {this.activate = activate; return this;}
	public boolean isActivate() {return activate;}
	
	public Command setRank(int rank) {this.rank = rank; return this;}
	public int getRank() {return rank;}
	
	public String getName() {return name;}
	
	@Override
	public String toString() {
		return "/" + name;
	}
	
	static {new Commands();}

}
