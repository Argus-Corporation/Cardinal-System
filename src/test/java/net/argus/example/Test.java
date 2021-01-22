package net.argus.example;

import java.io.File;
import java.net.MalformedURLException;

import net.argus.system.InitializedSplash;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.Display;

public class Test {
	
	public static void main(String[] args) throws MalformedURLException {
		InitializedSystem.initSystem(args, new InitializedSplash("res/logo.png", Display.getWidhtDisplay() - 50, 0));
		InitializedSplash.getSplash().exit();
		/*Properties config = new Properties("config", "bin");
		
		Lang.setLang(config);*/
		
		System.out.println(new File("D:\\Django\\Document 1\\Git\\bin\\css/client.css").getPath());
		
		UserSystem.exit(0);
	}

}
