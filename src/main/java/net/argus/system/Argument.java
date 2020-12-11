package net.argus.system;

public class Argument {
	
	public static String getArgument(String[] args, String key) {
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("-" + key)) {
				if(args[i + 1].toCharArray()[0] == '"') {
					for(int j = i + 1; j < args.length; j++) {
						if(args[j].toCharArray()[args[j].length() - 1] == '"') {
							String text = "";
							for(int k = i + 1; k < j; k++) {
								text += args[k];
							}
							return text;
						}
					}
				}else {
					return args[i + 1];
				}
			}
			
		}
		return null;
	}

}
