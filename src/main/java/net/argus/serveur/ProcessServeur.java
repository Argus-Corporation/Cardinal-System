package net.argus.serveur;

import java.io.IOException;

import net.argus.serveur.command.Command;
import net.argus.serveur.command.Commands;
import net.argus.serveur.role.Role;
import net.argus.serveur.role.Roles;
import net.argus.util.CharacterManager;
import net.argus.util.Package;
import net.argus.util.PackageType;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class ProcessServeur extends Thread {
	
	private int userId;
	
	private ServeurSocketClient client;
	
	private String pseudo;
	private boolean running;
	
	private static ServeurManager manager;
	
	public static final int PASSWORD = -3;
	public static final int LOG_OUT = -2;
	public static final int COMMANDE = -1;
	public static final int MESSAGE = 0;
	public static final int SYSTEM = 1;
	public static final int PSEUDO = 2;
	
	public ProcessServeur(ServeurSocketClient client, int userId) {
		this.client = client;
		this.userId = userId;
		new Commands();
	}
	
	public void sendMessageToAllCLient(int pt, String msg) throws SecurityException {
		ServeurSocketClient user;
		for(int i = 0; i < Users.getServeurSocketClient().length; i++) {
			if((user = Users.getServeurSocketClient(i)) != null && user.getUserId() != userId) {
				sendMessage(pt, i, msg);
			}
		}
	}
	
	public void sendMessage(String msg) throws SecurityException {
		client.sendPackage(new Package(PackageType.SYSTEM, msg));
		client.sendPackage(new Package(PackageType.PSEUDO, "SYSTEM ALERTE"));			
	}
	
	public void sendMessage(int pt, int userId, String msg) throws SecurityException {
		Users.getServeurSocketClient(userId).sendPackage(new Package(pt, msg));
		if(pt == PackageType.SYSTEM.getId()) 
			Users.getServeurSocketClient(userId).sendPackage(new Package(PackageType.PSEUDO, "SYSTEM ALERTE"));			
		else
			Users.getServeurSocketClient(userId).sendPackage(new Package(PackageType.PSEUDO, pseudo));			
	}
	
	public Package getPackage() {return client.receivePackage();}
	
	
	public void receiveMessage() throws IOException, SecurityException {
		int msgId;
		String msg;
		
		Package pack = getPackage();
		
		msgId = pack.getType();
		msg = pack.getMessage();
		
		switch(msgId) {
			case PSEUDO:
				pseudo = msg;
				int numberPseudo = 1;
				while(Users.isClientPseudoExist(pseudo)) {
					pseudo = msg + "(" + numberPseudo + ")";
					numberPseudo++;
				}
				
				pseudo = CharacterManager.cut(pseudo, ' ');
				
				sendMessage(PackageType.PSEUDO.getId(), userId, pseudo);
				
				currentThread().setName("SERVEUR: " + pseudo.toUpperCase());
				client.setPseudo(pseudo);
				Debug.log("Pseudo changed to: " + pseudo);
				break;
	
			case MESSAGE:
				Debug.log("Message from " + pseudo + ": " + msg);
				sendMessageToAllCLient(PackageType.MESSAGE.getId(), msg);
				break;
				
			case SYSTEM:
				int ver = 0;
				
				try {ver = Integer.valueOf(msg);}
				catch(Exception e) {
					Debug.log("Error: client version is not valid");
					client.logOut("corrupt version");
					ThreadManager.stop(this);
				}
				
				Debug.log("Client version: " + ver);
				if(ver != client.getServeurParent().getVersion()) {
					client.logOut("version");
					ThreadManager.stop(this);
				}
				break;
				
			case COMMANDE:
				String com;
				Command command;
				
				try {com = msg.toUpperCase().substring(0, msg.indexOf(" "));}
				catch(IndexOutOfBoundsException e) {com = msg.toUpperCase();}
				
				command = Command.getCommand(com);
				
				if(command == null) {
					sendMessage("This command is not existed");
					break;
				}
				
				Debug.log("Command " + com);
				
				command.execut(msg.split(" "), client);
				break;
				
			case LOG_OUT:
				client.close(msg);
				
				sendMessageToAllCLient(PackageType.SYSTEM.getId(), pseudo + " just disconnected");
				ThreadManager.stop(this);
				break;
				
			case PASSWORD:
				String password = msg;
				
				client.setRole(Role.getRole(password) == null ? Roles.DEFAULT : Role.getRole(password));
				break;
		}
		
		if(manager != null) manager.receivePackage(pack, this);
	}
	
	public void run() {
		ThreadManager.addThread(this);	
		running = true;
		
		sendMessageToAllCLient(PackageType.SYSTEM.getId(), "A client has just connected");
		while(client.getServeurParent().isRunning()) {
			try {receiveMessage();}
			catch(IOException | SecurityException e) {
				Debug.log("ERROR: Client diconected");
				
				try {if(running)sendMessageToAllCLient(PackageType.SYSTEM.getId(), pseudo + " just disconnected");
				}catch(SecurityException e1) {e1.printStackTrace();}
				
				ThreadManager.stop(this);
			}
		}
	}
	
	public static void addServeurManager(ServeurManager manager) {ProcessServeur.manager = manager;}
	
	public String getPseudo() {return pseudo;}
	public int getUserId() {return userId;}
	public void setRunning(boolean running) {this.running = running;}

}
