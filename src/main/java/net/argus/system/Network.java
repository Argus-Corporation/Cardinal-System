package net.argus.system;

import java.io.IOException;
import java.net.URL;

public class Network {
	
	private static boolean connect;
	private static boolean oneCheck;
	
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
	
}
