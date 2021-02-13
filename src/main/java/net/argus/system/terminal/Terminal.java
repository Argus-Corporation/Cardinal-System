package net.argus.system.terminal;

import java.io.File;

import net.argus.system.InitializationSystem;
import net.argus.system.UserSystem;

@Deprecated
public class Terminal extends Thread {
	
	private String directory = System.getProperty("user.dir");
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
	
	private void execut(String[] com) {
		switch(com[0]) {
			case "echo":
				String arg = "";
				for(int i = 1; i < com.length; i++)
					arg += com[i] + " ";
				
				System.out.println(arg);
				break;
				
			case "cd":
				String temDir = directory + File.separator + com[1];
				String dd;
				String direct = "";
				for(int i = 1; i < com.length; i++)
					direct += com[i] + " ";
				
				if(System.getProperty("os.name").startsWith("Win")) {
					dd = directory.substring(0, 1);
					if(direct.startsWith("/") || direct.startsWith("\\") || direct.contains(":"))
						if(direct.contains(":"))
							temDir = direct;
						else
							temDir = dd + ":" + File.separator + com[1];
				}
				if(direct.startsWith("/") || direct.startsWith("\\"))
					temDir = direct;
				if(new File(temDir).exists())
					directory = temDir;
				break;
				
			case "cls":
				for(int i = 0; i < 1000; i++)
				     System.out.println("\n") ;
				break;
				
			case "exit":
				stop();
				break;
		}
	}
	
	private String getPrefix() {
		return userName + "@" + pcName + ":~" + directory.substring(directory.indexOf(File.separator)) + "$ ";
	}
	
	public static void main(String[] args) {
		InitializationSystem.initSystem(new String[] {"-project.name", "Cardinal-System", "-id", "0xdev"}, UserSystem.getDefaultInitializedSystemManager());
		new Terminal().run();
		
	}

}
