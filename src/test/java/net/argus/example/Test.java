package net.argus.example;

import java.net.MalformedURLException;

import net.argus.system.InitializedSplash;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.Display;

public class Test {
	
	public static void main(String[] args) throws MalformedURLException {
		InitializedSystem.initSystem(args, new InitializedSplash("res/logo.png", Display.getWidhtDisplay() - 50, 1000));
		InitializedSplash.getSplash().exit();
		
		UserSystem.exit(0);
	}

}
