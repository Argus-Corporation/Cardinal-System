package net.argus.system.terminal;

import java.io.File;

import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;

public class Terminal extends Thread {
	
	private String directory = System.getProperty("user.home");
	private String userName = System.getProperty("user.name");
	private String pcName = System.getProperty("user");
	
	private boolean running;
	
	public Terminal() {
		running = true;
		System.out.println(pcName);
	}
	
	public void run() {
		while(running) {
			System.out.print(getPrefix());
			String com = UserSystem.in.nextLine();
			execut(com.split(" "));
		}
	}
	
	@SuppressWarnings("deprecation")
	private void execut(String[] com) {
		switch(com[0]) {
			case "echo":
				String arg = "";
				for(int i = 1; i < com.length; i++)
					arg += com[i] + " ";
				
				System.out.println(arg);
				break;
				
			case "cls":
				for(int i = 0; i < 1000; i++)
				     System.out.println("\n") ;
				break;
				
			case "exit":
				currentThread().stop();
				break;
		}
	}
	
	private String getPrefix() {
		return "[" + userName + "@" + directory.substring(directory.indexOf(File.separator)) + "~]$ ";
	}
	
	public static void main(String[] args) {
		InitializedSystem.initSystem(new String[] {"-project.name", "Cardinal-System", "-id", "0xdev"}, UserSystem.getDefaultInitializedSystemManager());
		new Terminal().run();
	}

}
