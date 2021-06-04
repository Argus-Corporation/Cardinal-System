package net.argus.example;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Test {
	
	public static void main(String[] args) throws MalformedURLException, URISyntaxException, UnknownHostException {
		Enumeration<?> p = System.getProperties().keys();
		Enumeration<?> p1 = System.getProperties().elements();
		
		while(p.hasMoreElements())
			System.out.println(p.nextElement() + "=" + p1.nextElement());
	}
	
}