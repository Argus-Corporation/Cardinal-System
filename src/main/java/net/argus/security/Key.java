package net.argus.security;

import net.argus.system.InitializationSystem;
import net.argus.util.ArrayManager;
import net.argus.util.Math;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class Key {

	private double password;

<<<<<<< HEAD
	private final char[] caracter = new char[] {'®', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
=======
	private static final char[] caracter = new char[] {'®', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
>>>>>>> dev
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '?', ',', ';', '.', ':', '/', '!', '§', 'ù',
			'%', '*', 'µ', '¨', '$', '£', '¤', '<', '>', '#', '&', 'é', '(', '[', '-', '|', 'è', '_', '\\',
			'ç', '^', 'à', '@', ')', '°', ']', '=', '+', '}', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '²', '\n', '\r', '\b', '\t', '\'', '’', 'ê', '®', 'Ì', 'ì', '{', '"', ' '};
<<<<<<< HEAD
=======
	
>>>>>>> dev
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
<<<<<<< HEAD
		//Key k = new Key("azertyuiopmlkjhgfd123456789");
		
		//String str = "1.4919529937178958E35\b1.686853199345494E35\b9.949954433122245E34\b1.2186155662546475E35\b1.1258718920312916E35\b1.6571009159915556E34\b8.994676700686313E34\b1.4893885172162805E35\b1.3269808857693695E35\b2.1107040756473672E35\b6.664979948906109E35\b8.489261495933616E34\b";
		
=======
		Key k = new Key("$^Ã¹**^$Ã¹m$Ã¹mefsd^mÃ¹6548#5{DSG3d47g4354j4Ã¹4$*84mi1olukjhgf85j#[|'(--Ã¨_k45");
		
		System.out.println(k.decrypt(k.crypt("hello xx")));
>>>>>>> dev
		
		//System.out.println(k.crypt("hello world!"));
	}

}
