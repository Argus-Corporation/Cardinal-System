package net.argus.example;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.Socket;
import java.net.UnknownHostException;

public class SockTest {
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		/*String hostName = "services1.paris1.alwaysdata.com";
		try
	    {
	        InetAddress inet = InetAddress.getByName(hostName);

	        boolean status = inet.isReachable(5000);

	        if (status)
	        {
	            System.out.println(inet.getCanonicalHostName() + " Host Reached\t" + Inet6Address.getByName(hostName).getHostAddress());
	        }
	        else
	        {
	            System.out.println(inet.getCanonicalHostName() + " Host Unreachable");
	        }

	       // System.out.println(inet.getCanonicalHostName() + " Host Reached\t" + java.net.Inet6Address.getByName(hostName).getHostAddress());
	    }
	    catch (UnknownHostException e)
	    {
	        System.err.println("Host does not exists");
	    }
	    catch (IOException e)
	    {
	        System.err.println("Error in reaching the Host");
	    }
		*/
		System.out.println(Inet6Address.getByName("services1.paris1.alwaysdata.com"));
		@SuppressWarnings({ "unused", "resource" })
		Socket soc = new Socket(Inet6Address.getByName("services1.paris1.alwaysdata.com"), 8300);
		
	}

}
