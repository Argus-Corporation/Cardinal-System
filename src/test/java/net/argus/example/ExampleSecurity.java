package net.argus.example;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.argus.security.Key;
import net.argus.system.InitializedSystem;
import net.argus.util.debug.Debug;

public class ExampleSecurity {
	
	public ExampleSecurity() {
		Key key = new Key("^m$ù*ù*ù[*^fùg*sFDHfdg^d*ù*¨%µ%Hµ¨Pùk;f");
		
		String text = key.crypt("\"download: {");
		Debug.log("Text cripted: " + text);
		
		Debug.log("Text uncripted: " + key.decrypt(text));
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		InitializedSystem.initSystem(args);
		new ExampleSecurity();
	}

}
