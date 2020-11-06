package net.argus.serveur.command;

import net.argus.serveur.ServeurSocketClient;

public interface CommandListener {

	public abstract void preExecute() throws CommandExeption;
	public abstract void execute(String[] com, ServeurSocketClient ssc) throws CommandExeption, SecurityException;
	public abstract void postExecute() throws CommandExeption;
	
}
