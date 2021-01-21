package net.argus.example;

import java.io.FileNotFoundException;
import java.io.IOException;

import net.argus.security.Key;
import net.argus.system.InitializedSystem;
import net.argus.util.debug.Debug;

public class ExampleSecurity {
	
	public ExampleSecurity() {
		Key key = new Key("ljngsk;gn:lsn�[*^f�g*sFDHfdg^d*�*�%�%H��P�k;f");
		
		String text = key.crypt("a-ha");
		Debug.log("Text crypted: " + text);
		
		Debug.log("Text uncrypted: " + key.decrypt(text));
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		InitializedSystem.initSystem(args);
		new ExampleSecurity();
	}

}
