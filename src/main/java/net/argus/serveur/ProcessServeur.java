package net.argus.serveur;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.serveur.command.Command;
import net.argus.serveur.command.Commands;
import net.argus.serveur.role.Role;
import net.argus.serveur.role.Roles;
import net.argus.util.CharacterManager;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageObject;
import net.argus.util.pack.PackageType;

public class ProcessServeur extends Thread {
	
	private int userId;
	
	private ServeurSocketClient client;
	
	private String pseudo;
	private boolean running;
	private boolean processLogOuting;
	
	private static ServeurManager manager;
	
	public static final int PASSWORD = -3;
	public static final int LOG_OUT = -2;
	public static final int COMMANDE = -1;
	public static final int MESSAGE = 0;
	public static final int SYSTEM = 1;
	public static final int PSEUDO = 2;
	public static final int ARRAY = 3;
	public static final int FILE = 4;
	
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
		client.sendPackage(new Package(new PackageBuilder(PackageType.SYSTEM.getId()).addValue("message", msg).addValue("pseudo", "SYSTEM ALERT")));		
	}
	
	public void sendMessage(int pt, int userId, String msg) throws SecurityException {
		String pseudo;
		
		if(pt == PackageType.SYSTEM.getId()) 
			pseudo = "SYSTEM ALERTE";			
		else
			pseudo = this.pseudo;	
		
		PackageBuilder bui = new PackageBuilder(pt);
		bui.addValue("message", msg);
		bui.addValue("pseudo", pseudo);
		
		Users.getServeurSocketClient(userId).sendPackage(new Package(bui));
	}
	
	public Package getPackage() throws SecurityException {return client.nextPackage();}
	
	
	public void receiveMessage() throws IOException, SecurityException {
		int msgId;
		String msg;
		
		Package pack = getPackage();
		
		msgId = pack.getType();
		
		
		switch(msgId) {
			case PSEUDO:
				pseudo = pack.getValue("pseudo");
				
				int numberPseudo = 1;
				
				while(Users.isClientPseudoExist(pseudo)) {
					pseudo = pseudo + "(" + numberPseudo + ")";
					numberPseudo++;
				}
				
				pseudo = CharacterManager.cut(pseudo, ' ');
				
				sendMessage(PackageType.PSEUDO.getId(), userId, pseudo);
				
				currentThread().setName("SERVEUR: " + pseudo.toUpperCase());
				client.setPseudo(pseudo);
				Debug.log("Pseudo changed to: " + pseudo);
				break;
	
			case MESSAGE:
				msg = pack.getValue("message");
				Debug.log("Message from " + pseudo + ": " + msg);
				
				sendMessageToAllCLient(PackageType.MESSAGE.getId(), msg);
				break;
				
			case SYSTEM:
				int ver = 0;
				
				msg = pack.getValue("version");
				
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
				
				msg = pack.getValue("command");
				
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
				msg = pack.getValue("message");
				
				sendMessageToAllCLient(PackageType.SYSTEM.getId(), pseudo + " just disconnected");
				
				client.close(msg);
				ThreadManager.stop(this);
				break;
				
			case PASSWORD:
				String password = pack.getValue("password");
				
				client.setRole(Role.getRole(password) == null ? Roles.DEFAULT : Role.getRole(password));
				break;
				
			case FILE:
				PackageBuilder bui = new PackageBuilder(FILE);
				PackageObject obj = new PackageObject("value");
				
				obj.addItem("fileName", pack.getObject("value").getValue("fileName").toString());
				obj.addItem("extention", pack.getObject("value").getValue("extention").toString());
				obj.addItemArray("data", pack.getObject("value").getArrayValue("data"));
				
				bui.addValue("value", obj);
				
				//for(CJSONObject pse : pack.getArray("clientReceivers"))
					//Users.getServeurSocketClient(pse.toString()).sendPackage(new Package(bui));
				
				new Thread(new Runnable() {
					public void run() {
						try {
							Users.getServeurSocketClient("lol").sendPackage(new Package(bui));
						}catch(SecurityException e) {e.printStackTrace();}
						
					}
				}).start();
				
				break;
		}
		
		if(manager != null) manager.receivePackage(pack, this);
	}
	
	public void run() {
		ThreadManager.addThread(this);	
		running = true;
		
		try {sendMessageToAllCLient(PackageType.SYSTEM.getId(), "A client has just connected");}
		catch(SecurityException e2) {e2.printStackTrace();}
		
		while(client.getServeurParent().isRunning()) {
			try {receiveMessage();}
			catch(IOException | SecurityException e) {
				if(!processLogOuting) {
					Debug.log("ERROR: Client diconected");
				
					try {if(running) sendMessageToAllCLient(PackageType.SYSTEM.getId(), pseudo + " just disconnected");}
					catch(SecurityException e1) {e1.printStackTrace();}
					
					ThreadManager.stop(this);
				}
			}
		}
	}
	
	public static void addServeurManager(ServeurManager manager) {ProcessServeur.manager = manager;}
	
	public String getPseudo() {return pseudo;}
	public int getUserId() {return userId;}
	public void setRunning(boolean running) {this.running = running;}
	public void setprocessLogOuting(boolean processLogOuting) {this.processLogOuting = processLogOuting;}

}
