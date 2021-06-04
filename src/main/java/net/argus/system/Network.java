package net.argus.system;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;

import net.argus.util.CharacterManager;

public class Network {
	
	private static boolean connect;
	private static boolean oneCheck;
	
	public static final int MAX_PORT_VALUE = 65536;
	
	public static boolean checkConnection() {
		oneCheck = true;
		
		try {new URL("https://www.google.com/").openConnection().connect();}
		catch(IOException e) {return false;}
		
		return true;
	}
	
	public static boolean isConnected() {
		if(!oneCheck)
			connect = checkConnection();
		
		return connect;
	}
	
	public static boolean isIp(String host) {
		if(checkConnection())
			try{InetAddress.getByName(host);}
			catch(IOException e) {return false;}
		
		return true;
	}
	
	public static boolean isPort(String port) {
		if(CharacterManager.isNumber(port) && Integer.valueOf(port) <= MAX_PORT_VALUE)
			return true;
		
		return false;
	}
	
}
