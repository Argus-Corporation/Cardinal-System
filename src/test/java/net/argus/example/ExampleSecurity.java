package net.argus.example;

import net.argus.security.Key;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.debug.Debug;

public class ExampleSecurity {
	
	public ExampleSecurity() {
		Key key = new Key("^m$�*�*�*^f�g*fdg^d*�*�%�%L%Ml�OL�%ML*$^l�*L��P�k;f");
		
		String text = key.crypt("Hello world!");
		Debug.log("Text cripted: " + text);
		
		Debug.log("Text uncripted: " + key.decrypt(text));
	}
	
	public static void main(String[] args) {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		new ExampleSecurity();
	}

}
