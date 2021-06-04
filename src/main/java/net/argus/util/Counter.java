package net.argus.util;

public class Counter {
	
	public static int countOccurrences(String chaine, char search) {
		int count = 0;
		for(int i = 0; i < chaine.length(); i++) {
		    if(chaine.charAt(i) == search) {
		        count++;
		    }
		}
		return count;
	}

}
