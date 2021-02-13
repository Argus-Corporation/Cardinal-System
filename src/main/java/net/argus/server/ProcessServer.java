package net.argus.server;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.server.command.Command;
import net.argus.server.command.Commands;
import net.argus.server.role.Role;
import net.argus.server.role.Roles;
import net.argus.util.CharacterManager;
import net.argus.util.ErrorCode;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageType;

public class ProcessServer extends Thread {
	
	private int userId;
	
	private ServerSocketClient client;
	
	private boolean running;
	private boolean processLogOuting;
	
	private static ServerManager manager;
	
	public static final int UNCONNECTION = -6;
	public static final int CONNECTION = -5;
	
	public static final int PASSWORD = -3;
	public static final int LOG_OUT = -2;
	public static final int COMMANDE = -1;
	public static final int MESSAGE = 0;
	public static final int SYSTEM = 1;
	public static final int PSEUDO = 2;
	public static final int ARRAY = 3;
	public static final int FILE = 4;
	public static final int NOTIFY = 5;
	
	public ProcessServer(ServerSocketClient client, int userId) {
		this.client = client;
		this.userId = userId;
		new Commands();
	}
	
	public void sendMessageToAllCLient(int pt, String msg) throws SecurityException {
		ServerSocketClient user;
		for(int i = 0; i < Users.getServerSocketClient().length; i++) {
			if((user = Users.getServerSocketClient(i)) != null && user.getUserId() != userId) {
				sendMessage(pt, i, msg);
			}
		}
	}
	
	public void sendMessage(String msg) throws SecurityException {
		client.sendPackage(new Package(new PackageBuilder(PackageType.SYSTEM.getId()).addValue("message", msg).addValue("pseudo", "SYSTEM ALERT")));		
	}
	
	public void sendMessage(PackageType pt, String msg, String pseudo, String suffix) throws SecurityException {
		this.sendMessage(pt.getId(), msg, pseudo, suffix);
	}
	
	public void sendMessage(int pt, String msg, String pseudo, String suffix) throws SecurityException {
		client.sendPackage(new Package(new PackageBuilder(pt).addValue("message", msg).addValue("pseudo", pseudo + "-" + suffix)));
	}
	
	public void sendMessage(int pt, int userId, String msg) throws SecurityException {
		String pseudo;
		
		if(pt == PackageType.SYSTEM.getId()) 
			pseudo = "SYSTEM ALERTE";			
		else
			pseudo = client.getPseudo();	
		
		PackageBuilder bui = new PackageBuilder(pt);
		bui.addValue("message", msg);
		bui.addValue("pseudo", pseudo);
		
		Users.getServerSocketClient(userId).sendPackage(new Package(bui));
	}
	
	public Package getPackage() throws SecurityException {return client.nextPackage();}
	
	public void receiveMessage() throws IOException, SecurityException {
		int msgId;
		String msg;
		
		Package pack = getPackage();
		
		msgId = pack.getType();
		
		switch(msgId) {
			case PSEUDO:
				msg = pack.getValue("pseudo");
				
				setPseudo(msg);
				break;
	
			case MESSAGE:
				msg = pack.getValue("message");
				Debug.log("Message from " + client.getPseudo() + ": " + msg);
				
				sendMessageToAllCLient(PackageType.MESSAGE.getId(), msg);
				break;
				
			case SYSTEM:
				int ver = 0;
				
				msg = pack.getValue("version");
				
				try {ver = Integer.valueOf(msg);}
				catch(Exception e) {
					Debug.log("Error: client version is not valid");
					client.logOut("Corrupt version", ErrorCode.coruptVersion);
					ThreadManager.stop(this);
				}
				
				Debug.log("Client version: " + ver);
				if(ver != Server.getVersion()) {
					client.logOut("Incompatile version", ErrorCode.version);
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
					sendMessage("This command doesn't exist");
					break;
				}
				
				Debug.log("Command " + com);
				
				command.execut(msg.split(" "), client);
				break;
				
			case LOG_OUT:
				msg = pack.getValue("message");
				
				client.close(msg);
				ThreadManager.stop(this);
				break;
				
			case PASSWORD:
				String password = pack.getValue("password");
				
				client.setRole(Role.getRole(password) == null ? Roles.DEFAULT : Role.getRole(password));
				break;
				
		}
		
		if(manager != null) manager.receivePackage(pack, this);
	}
	
	public void setPseudo(String pseudo) throws SecurityException {
		int numberPseudo = 1;
		String basePseudo = pseudo;
		
		while(Users.isClientPseudoExist(pseudo)) {
			pseudo = basePseudo + "(" + numberPseudo + ")";
			numberPseudo++;
		}
		
		pseudo = CharacterManager.cut(pseudo, ' ');
		
		sendMessage(PackageType.PSEUDO.getId(), userId, pseudo);
		
		currentThread().setName("SERVER: " + pseudo.toUpperCase());
		client.setPseudo(pseudo);
		Debug.log("Pseudo changed to: " + pseudo);
	}
	
	@Override
	public void run() {
		ThreadManager.addThread(this);
		running = true;
		
		try {
			sendMessageToAllCLient(PackageType.SYSTEM.getId(), "A client has just connected");
			sendMessage("You are connected");
		}catch(SecurityException e2) {e2.printStackTrace();}
		
		while(client.getServerParent().isRunning()) {
			try {receiveMessage();}
			catch(IOException | SecurityException e) {
				try {client.logOut(ErrorCode.error);}
				catch(IOException | SecurityException e2) {e2.printStackTrace();}
				
				if(!processLogOuting) {
					Debug.log("ERROR: Client diconected");
				
					try {if(running) sendMessageToAllCLient(PackageType.SYSTEM.getId(), client.getPseudo() + " just disconnected");}
					catch(SecurityException e1) {e1.printStackTrace();}
					
					ThreadManager.stop(this);
				}
			}
		}
	}
	
	public static void addServerManager(ServerManager manager) {ProcessServer.manager = manager;}
	
	public int getUserId() {return userId;}
	public void setRunning(boolean running) {this.running = running;}
	public void setprocessLogOuting(boolean processLogOuting) {this.processLogOuting = processLogOuting;}

}
