package net.argus.example;

import java.io.IOException;

import net.argus.serveur.Serveur;
import net.argus.serveur.role.Role;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;

public class ExampleServeur {
	
	public ExampleServeur() throws IOException {
		Serveur serv = new Serveur(100000, 11066);
		
		new Role("admin").setPassword("rt").setRank(10).registry();
		new Role("modo").setPassword("io").setRank(5).registry();
		
		serv.start();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		new ExampleServeur();
	}

}
