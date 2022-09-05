package net.argus.system;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import net.argus.Cardinal;
import net.argus.util.StringManager;

public class Network {
	
	private static boolean connect;
	private static boolean oneCheck;
	private static long checkTime = -1;
	
	private static long timeOut = 300000;
	
	public static final int MAX_PORT_VALUE = 65536;
	
	public static boolean checkConnection() {
		oneCheck = true;
		
		checkTime = System.currentTimeMillis();
		try {new URL(Cardinal.WEB).openConnection().connect();}
		catch(IOException e) {return false;}
		
		return true;
	}
	
	public static boolean isConnected() {
		if(!alreadyChecked())
			connect = checkConnection();
		
		return connect;
	}
	
	public static boolean isIp(String host) {
		try{InetAddress.getByName(host);}
		catch(IOException e) {return false;}
		
		return true;
	}
	
	public static boolean isPort(String port) {
		if(StringManager.isInteger(port) && Integer.valueOf(port) <= MAX_PORT_VALUE)
			return true;
		
		return false;
	}
	
	public static InetAddress getLocalHost() {try {return InetAddress.getLocalHost();}catch(UnknownHostException e) {return null;}}
	
	public static InetAddress getByName(String host) {try {return InetAddress.getByName(host);}catch(UnknownHostException e) {return null;}}
	
	public static boolean alreadyChecked() {
		return oneCheck && checkTime != -1 && System.currentTimeMillis() - checkTime <= timeOut;
	}
	
}
