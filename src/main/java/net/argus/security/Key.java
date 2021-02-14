package net.argus.security;

import net.argus.system.InitializationSystem;
import net.argus.util.ArrayManager;
import net.argus.util.Math;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class Key {

	private double password;

	private static final char[] caracter = new char[] {'®', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '?', ',', ';', '.', ':', '/', '!', '§', 'ù',
			'%', '*', 'µ', '¨', '$', '£', '¤', '<', '>', '#', '&', 'é', '(', '[', '-', '|', 'è', '_', '\\',
			'ç', '^', 'à', '@', ')', '°', ']', '=', '+', '}', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '²', '\n', '\r', '\b', '\t', '\'', '’', 'ê', '®', 'Ì', 'ì', '{', '"', ' '};
	
	public String key;
	public Key(String key) {
		this.key = key;
		double password = caracter.length;
		char[] keyChar = key.toCharArray();
		for(int i = 0; i < keyChar.length; i++) {
			for(int j = 0; j < caracter.length; j++) {
				if(caracter[j] == keyChar[i]) {
					password *= j;
					if(password == 0) password = Math.getPercentage(Math.sqrt(i), caracter.length);
				}
			}
		}
		this.password = password;
	}

	public String crypt(String chaine) throws SecurityException {
		char[] chaineChar = chaine.toCharArray();
		String crypt = "";
		
		for(int i = 0; i < chaineChar.length; i++) {
			for(int j = 0; j < caracter.length; j++) {
				if(caracter[j] == chaineChar[i])
					crypt += Double.toString((password / (caracter.length * j + 1)) * Math.sqrt(i != 0 ? i : 2)) + "\b";
			}
		}
		return crypt;
	}

	public String decrypt(String crypt) throws SecurityException {
		String temp = crypt;
		
		String[] word = ArrayManager.cut(temp, '\b');
		char[] result = new char[word.length];

		try {
			for(int i = 0; i < word.length; i++)
				result[i] = caracter[(int) ((password * Math.sqrt(i != 0 ? i : 2)) / (Double.valueOf(word[i]) * caracter.length))];
		}catch(IndexOutOfBoundsException e) {throw new SecurityException("decrypt key is corrupt");}
			
		String decrypt = new String(result);
		return decrypt;
	}

	public static void main(String[] args) throws SecurityException {
		InitializationSystem.initSystem(args);
		Debug.addBlackList(ThreadManager.PROGRESSE);
		Key k = new Key("$^Ã¹**^$Ã¹m$Ã¹mefsd^mÃ¹6548#5{DSG3d47g4354j4Ã¹4$*84mi1olukjhgf85j#[|'(--Ã¨_k45");
		
		System.out.println(k.decrypt(k.crypt("hello xx")));
		
		//System.out.println(k.crypt("hello world!"));
	}

}
