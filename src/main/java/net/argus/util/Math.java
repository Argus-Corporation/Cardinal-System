package net.argus.util;

import java.util.ArrayList;
import java.util.List;

import net.argus.number.Binary;
import net.argus.number.Hexadecimal;
import net.argus.number.Hexadecimal.CharHex;
import net.argus.number.Quaternaire;
import net.argus.system.InitializationSystem;
import net.argus.system.UserSystem;
import net.argus.util.debug.Debug;

public class Math {
	
	private static Convert<Integer, Integer> intConvert = new Convert<Integer, Integer>();
	
	public static int[] getPerfectNumber(int max) {
		List<Integer> allDiviser = new ArrayList<Integer>();
		List<Integer> perfect = new ArrayList<Integer>();
		Object[] obj = null;
		int[] in = null;
		int result = 0;
		
		ThreadManager.PROGRESSE.setTemporaryName();
		
		for(int n = 0; n < max; n++) {
			for(int i = 1; i < n; i++) if(n % i == 0) allDiviser.add(i);
			for(int j = 0; j < allDiviser.size(); j++) result += allDiviser.get(j);
			
			if(result == n && result != 0) perfect.add(result);
			allDiviser.clear();
			result = 0;
			Debug.log(n * 101 / max + "%");
		}
		
		obj = intConvert.toArray(perfect);
		in = new int[obj.length];
		
		for(int i = 0; i < obj.length; i++) in[i] = new Integer(obj[i].toString());
		ThreadManager.PROGRESSE.restorOldParameter();
		return in;
	}
	
	public static int[] getFirstNumber(int max) {
		return getFirstNumber(0, max);
	}
	
	public static int[] getFirstNumber(int min, int max) {
		List<Integer> allDiviser = new ArrayList<Integer>();
		List<Integer> first = new ArrayList<Integer>();
		Object[] obj = null;
		int[] in = null;
		
		ThreadManager.PROGRESSE.setTemporaryName();
		
		for(int n = min<2?2:min; n < max; n++) {
			for(int i = 1; i <= n; i++) if(n % i == 0) allDiviser.add(i);
			if(allDiviser.get(0) == 1 && allDiviser.get(1) == n) first.add(n);
			allDiviser.clear();
			//Debug.log(n * 101 / max + "%");
		}
		
		obj = intConvert.toArray(first);
		in = new int[obj.length];
		
		for(int i = 0; i < obj.length; i++) in[i] = new Integer(obj[i].toString());
		ThreadManager.PROGRESSE.restorOldParameter();
		return in;
	}
	
	public static double reverse(double n) {
		 return 1 / n;
	}
	
	public static boolean isOdd(int n) {
		return isDivisible(n, 2);
	}
	
	public static boolean isDivisible(int number, int div) {return number%div==0;}
	
	public static double square(double n) {
		return n * n;
	}
	
	public static double sqrt(double n) {
		double tmp;
		double d = n / 2;
		do {
			tmp = d;
			d = (tmp + (n / tmp)) / 2;
		}while((tmp - d) != 0);
		return d;
	}
	
	public static double pow(double a, double b) {
		double a0 = a;

		if(b == 0) return 1;

		for(int i = 0; i < b - 1; i++)
			a = a * a0;
		
		return a;
	}
	
	@Deprecated
	public static int random(int min, int max) {
		return -1;
	}
		
	public static int[] toIntArray(int number) {
		String num = Integer.toString(number);
		char[] chars = num.toCharArray();
		
		int[] fin = new int[chars.length];
		for(int i = 0; i < chars.length; i++)
			fin[i] = Integer.valueOf(chars[i]);
		
		return fin;
	}
		
	public static double getPercentage(double i, double numberMax) {return i * 100.0D / numberMax;}
	
	public static int abs(int a) {return (a < 0) ? -a : a;}
	
	public static Binary toBinary(long n) {
		List<Long> bin = new ArrayList<Long>();
		String binS = "";
		long n0 = n;
		
		do {
			bin.add(n0%2);
			n0 = n0 / 2;
		}while(n0 > 0);
		
		for(int i = bin.size() - 1; i >= 0; i--)
			binS += bin.get(i);
		
		return new Binary(binS);
	}
	
	public static Hexadecimal toHexadecimal(long n) {
		List<Character> hex = new ArrayList<Character>();
		String hexs = "";
		long n0 = n;

		do {
			hex.add(CharHex.getChar((int) (n0%16)));
			n0 = n0 / 16;
		}while(n0 > 0);
		
		for(int i = hex.size() - 1; i >= 0; i--)
			hexs += hex.get(i);
		
		return new Hexadecimal(hexs);
	}
	
	public static Quaternaire toQuaternaire(int n) {
		List<Long> bin = new ArrayList<Long>();
		String binS = "";
		long n0 = n;
		
		do {
			bin.add(n0%4);
			n0 = n0 / 4;
		}while(n0 > 0);
		
		for(int i = bin.size() - 1; i >= 0; i--)
			binS += bin.get(i);
		
		return new Quaternaire(binS);
	}
	
}
