package net.argus.security;

import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.ArrayManager;
import net.argus.util.Math;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class Key {

	private double password;

	private final char[] caracter = new char[] {'�', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
			'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '?', ',', ';', '.', ':', '/', '!', '�', '�',
			'%', '*', '�', '�', '$', '�', '�', '<', '>', '#', '&', '�', '(', '[', '-', '|', '�', '_', '\\',
			'�', '^', '�', '@', ')', '�', ']', '=', '+', '}', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '�', '\n', '\r', '\b', '\t', '\'', '�', '�', '�', '�', '�', ' '};

	public Key(String key) {
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
			for (int i = 0; i < word.length; i++)
				result[i] = caracter[(int) ((password * Math.sqrt(i != 0 ? i : 2)) / (Double.valueOf(word[i]) * caracter.length))];
		}catch(IndexOutOfBoundsException e) {throw new SecurityException("decrypt key is corrupt");}
			
		String decrypt = new String(result);
		return decrypt;
	}

	public static void main(String[] args) throws SecurityException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		Debug.addBlackList(ThreadManager.PROGRESSE);
		
		Key k = new Key("*^Ã¹$^fÂ®â˜»â–²%2d$dsÃ¹^f*$dsdsfsdf4s5d2d45fds52f4dsf5426465c42sdf546Ã¹*^$65^fÃ¹$^d*s*");
		//Key k2 = new Key("*^Ã¹$^fd$dsÃ¹^f*$dsdsfsdf4s5d2b45fds52f4dsf5426465c42sdf546Ã¹*^$65^fÃ¹$^d*s*");
		
		/*String test = k2.crypt("La nature est tout ce qu'on voit,\r\n" + 
				"Tout ce quâ€™on veut, tout ce qu'on aime.\r\n" + 
				"Tout ce quâ€™on sait, tout ce quâ€™on croit,\r\n" + 
				"Tout ce que lâ€™on sent en soi-mÃªme.\r\n" + 
				"\r\n" + 
				"Elle est belle pour qui la voit,\r\n" + 
				"Elle est bonne Ã  celui qui lâ€™aime,\r\n" + 
				"Elle est juste quand on y croit\r\n" + 
				"Et quâ€™on la respecte en soi-mÃªme.\r\n" + 
				"\r\n" + 
				"Regarde le ciel, il te voit,\r\n" + 
				"Embrasse la terre, elle tâ€™aime.\r\n" + 
				"La vÃ©ritÃ© câ€™est ce quâ€™on croit\r\n" + 
				"En la nature câ€™est toi-mÃªme.");*/
		//System.out.println("\n" + test + "\n");
		String test = k.crypt("FrÃ©dÃ©rique");
		System.out.println(test);
		System.out.println(k.decrypt(test));
	}

}
