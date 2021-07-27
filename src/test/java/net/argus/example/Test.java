package net.argus.example;

import java.net.InetAddress;
import java.net.UnknownHostException;

import net.argus.exception.InstanceException;

public class Test {
	
	public static void main(String[] args) throws InstanceException, UnknownHostException {
		System.out.println(InetAddress.getByName("localhost").getHostName());
	}
	
}
