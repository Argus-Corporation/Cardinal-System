package net.argus.example;

import java.io.IOException;
import java.net.UnknownHostException;

import net.argus.client.Client;

public class Test {
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		ExampleServeur.main(args);
		Thread.sleep(1000);
		for(int i = 0; i < 10; i++) {
			Client cli = new Client("", 11066);
			cli.setPseudo("hello");
			cli.setPassword("rt");
			cli.start();
		}
	}

}
