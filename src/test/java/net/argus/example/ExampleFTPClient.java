package net.argus.example;

import java.io.IOException;

import net.argus.exception.SecurityException;
import net.argus.io.ftp.FTPClient;

public class ExampleFTPClient {
	
	public static void main(String[] args) throws IOException, SecurityException {
		FTPClient client = new FTPClient("host", "user", "password");
		
		System.out.println(client);
		
		client.transfert("C:/path/test.txt", "/floder/");
	}

}
