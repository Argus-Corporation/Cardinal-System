package net.argus.number;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.Math;

public class Quaternaire {
	
	private String value;
	
	public Quaternaire(String value) {
		if(isValidFormat(value)) this.value = value;
		else throw new NumberFormatException();
	}
	
	public Quaternaire(int n) {
		this(String.valueOf(n));
	}
	
	public Quaternaire(long n) {
		this(String.valueOf(n));
	}
	
	public Quaternaire(float n) {
		this(String.valueOf(n));
	}
	
	public Quaternaire(double n) {
		this(String.valueOf(n));
	}
	
	public static boolean isValidFormat(String o) {
		char[] ch = o.toCharArray();
		
		for(char caracter : ch)
			if(caracter != '0' && caracter != '1' && caracter != '2' && caracter != '3')
				return false;
		
		return true;
	}
	
	public static Quaternaire valueOf(int n) {
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
	
	@Override
	public String toString() {
		return value;
	}
	
	public int toInt() {
		int[] nums = getNumber();
		int n = 0;
		
		for(int i = 0, j = nums.length - 1; i < nums.length; i++, j--)
			n += nums[i] * Math.pow(4, j);
		
		return n;
	}
	
	public int[] getNumber() {
		int[] nums = new int[value.length()];
		for(int i = 0; i < value.length(); i++)
			nums[i] = Integer.valueOf(new String(Character.toString(value.toCharArray()[i])));
		
		return nums;
	}

}
