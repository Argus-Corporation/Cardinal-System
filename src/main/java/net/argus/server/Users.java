package net.argus.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

import net.argus.file.FileSave;
import net.argus.security.Key;
import net.argus.util.ErrorCode;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;
import net.argus.exception.SecurityException;

public class Users {
	
	private static ServerSocketClient[] clients;
	private static FileSave banList = new FileSave("banip", "", new String[] {"ban", "ip", "name"});
	private Key key;
	private static int maxClient;
	
	public Users(int maxClient, Key key) {
		Users.maxClient = maxClient;
		this.key = key;
		clients = new ServerSocketClient[maxClient];
	}
	
	public Users(int maxClient) {
		this(maxClient, null);
	}
	
	public synchronized void addUser(Server server, Socket client) throws SecurityException, IOException {
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] == null) {
				try {
					clients[i] = key!=null?new ServerSocketClient(server, client, key, i):new ServerSocketClient(server, client, i);
				}catch(IllegalAccessException e) {
					clients[i] = null;
					return;
				}
				Debug.log("Client added");
				return;
			}
		}
		Debug.log("Server full");
		
		try{new ServerSocketClient(server, client, -1);}
		catch(IllegalAccessException e) {}
		
	}
	
	public void closeAll(int userId) throws IOException, SecurityException {
		for(int i = 0; i < clients.length; i++) {
			if(clients[i] != null) {
				//if(i != userId)
					ThreadManager.stop(clients[i].getProcessServer());
				clients[i].logOut("Kick all", ErrorCode.kick);
			}
		}
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
	
	public static ServerSocketClient[] getServerSocketClient() {return clients;}
	
	public static ServerSocketClient getServerSocketClient(int userId) {return clients[userId];}
	
	public static ServerSocketClient getServerSocketClient(String pseudo) {
		for(int i = 0; i < clients.length; i++)
			if(clients[i] != null && clients[i].getPseudo().toUpperCase().equals(pseudo.toUpperCase()))
				return clients[i];
		
		return null;
	}
	
	public Key getKey() {return key;}
	public static FileSave getBanIpFile() {return banList;}
	
	public static int getMaxClient() {return maxClient;}

}
