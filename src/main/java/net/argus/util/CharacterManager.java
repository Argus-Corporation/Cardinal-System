package net.argus.util;

public class CharacterManager {
	
	public static String cut(String enter, char cut) {
		char[] carater = enter.toCharArray();
		for(int i = 0; i < carater.length; i++) {
			if(carater[i] == ' ') {
				ArrayManager.remove(carater, i);
			}
		}
		return new String(carater);
	}
	
	public static String toUpperCase(String enter) {
		char[] chars = enter.toCharArray();
		char first = Character.toUpperCase(chars[0]);
		chars[0] = first;
		
		return new String(chars);
		
	}

}
