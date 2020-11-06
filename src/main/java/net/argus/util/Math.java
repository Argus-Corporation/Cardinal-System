package net.argus.util;

import java.util.ArrayList;
import java.util.List;

import net.argus.system.InitializedSystem;
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
		List<Integer> allDiviser = new ArrayList<Integer>();
		List<Integer> first = new ArrayList<Integer>();
		Object[] obj = null;
		int[] in = null;
		
		ThreadManager.PROGRESSE.setTemporaryName();
		
		for(int n = 2; n < max; n++) {
			for(int i = 1; i <= n; i++) if(n % i == 0) allDiviser.add(i);
			if(allDiviser.get(0) == 1 && allDiviser.get(1) == n) first.add(n);
			allDiviser.clear();
			Debug.log(n * 101 / max + "%");
		}
		
		obj = intConvert.toArray(first);
		in = new int[obj.length];
		
		for(int i = 0; i < obj.length; i++) in[i] = new Integer(obj[i].toString());
		ThreadManager.PROGRESSE.restorOldParameter();
		return in;
	}
	
	public static boolean isDivisible(int number, int div) {return number%div==0?true:false;}
	
	public static native double square(double n);
	
	public static native double sqrt(double n);
	
	public static native int random(int min, int max);
	
	public static double getPercentage(double i, double numberMax) {return i * 100.0D / numberMax;}
	
	private static native int[] toIntArray0(int number, int size);
	
	public static int[] toIntArray(int number) {return toIntArray0(number, Integer.toString(number).length());}
	
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
	
	static {UserSystem.loadLibrary("math");}
	
	public static void main(String[] args) {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		
		//System.out.println(toBinary(101));
		System.out.println(new Binary(100101110));
		UserSystem.exit(0);
	}
	
}
