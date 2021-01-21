package net.argus.serveur;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import net.argus.exception.SecurityException;
import net.argus.file.FileSave;
import net.argus.security.Key;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class Users {
	
	private static ServeurSocketClient[] clients;
	private static FileSave banList = new FileSave("banip", "", new String[] {"ban", "ip", "name"});
	private static Key key;
	private static int maxClient;
	
	public Users(int maxClient, Key key) {
		Users.maxClient = maxClient;
		Users.key = key;
		clients = new ServeurSocketClient[maxClient];
	}
	
	public Users(int maxClient) {
		Users.maxClient = maxClient;
		clients = new ServeurSocketClient[maxClient];
	}
	
	public synchronized void addUser(Serveur serveur, Socket client) throws IOException {
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] == null) {
				try {
					clients[i] = key!=null?new ServeurSocketClient(serveur, client, key, i):new ServeurSocketClient(serveur, client, i);
				}catch(IllegalAccessException e) {
					clients[i] = null;
					return;
				}
				Debug.log("Client added");
				return;
			}
		}
		Debug.log("Serveur full");
		
		try{new ServeurSocketClient(serveur, client, 0);}
		catch(IllegalAccessException e) {}
		
	}
	
	public void close(int userId) throws IOException, SecurityException {
		for(int i = 0; i < clients.length; i++) {
<<<<<<< Updated upstream:src/main/java/net/argus/serveur/Users.java
			if(clients[i] != null && i != userId)
				ThreadManager.stop(clients[i].getProcessServeur());
			
			clients[i].logOut("Kick all");
=======
			if(clients[i] != null) {
				if(i != userId)
					ThreadManager.stop(clients[i].getProcessServer());
				clients[i].logOut("Kick all", ErrorCode.kick);
			}
>>>>>>> Stashed changes:src/main/java/net/argus/server/Users.java
		}
	}
	
	public void close() throws IOException, SecurityException {
		close(-1);
	}
	
	public static boolean isClientPseudoExist(String pseudo) {
		for(int i = 0; i < clients.length; i++)
			if(clients[i] != null && clients[i].getPseudo().toUpperCase().equals(pseudo.toUpperCase()))
				return true;
		return false;
	}
	
	public static int numberPseudo(String pseudo) {
		int count = 0;
		for(int i = 0; i < clients.length; i++)
			if(clients[i] != null && clients[i].getPseudo().equals(pseudo))
				count++;
		return count;
	}
	
	public static boolean isBanned(Socket client) throws FileNotFoundException {
		String thisIp = client.getInetAddress().toString().substring(1);
		
		for(int i = 1; i < banList.getNumberLine() + 1; i++) {
			if(banList.read(i).equals(thisIp)) {
				return true;
			}
		}
		return false;
	}
	
	public static int getClientConnected() {
		int count = 0;
		for(int i = 0; i < clients.length; i++)
			if(clients[i] != null)
				count++;
		return count;
	}
	
	public static ServeurSocketClient[] getServeurSocketClient() {return clients;}
	
	public static ServeurSocketClient getServeurSocketClient(int userId) {return clients[userId];}
	
	public static ServeurSocketClient getServeurSocketClient(String pseudo) {
		for(int i = 0; i < clients.length; i++) {
			if(clients[i].getPseudo().toUpperCase().equals(pseudo.toUpperCase())) {
				return clients[i];
			}
		}
		return null;
	}
	
	public static Key getKey() {return key;}
	public static FileSave getBanIpFile() {return banList;}
	
	public static int getMaxClient() {return maxClient;}

}
