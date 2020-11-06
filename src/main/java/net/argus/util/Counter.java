package net.argus.util;

import java.io.FileNotFoundException;

import net.argus.file.Properties;

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
	
	public static int countOccurrences(Properties file, char search) throws FileNotFoundException {
		int count = 0;
		for(int i = 0; i < file.getNumberLine(); i++) {
			count += countOccurrences(file.getValue(file.getLine(i + 1)), search);
			count++;
		}
		return count;
	}

}
