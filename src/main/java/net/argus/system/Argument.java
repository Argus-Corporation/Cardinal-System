package net.argus.system;

public class Argument {
	
	public static String getArgument(String[] args, String key) {
		for(int i = 0; i < args.length; i++)
			if(args[i].equals("-" + key)) return args[i + 1];
		return null;
	}

}
