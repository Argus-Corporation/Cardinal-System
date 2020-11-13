package net.argus.number;

import net.argus.util.Math;

public class Binary {
	
	private String value;
	
	public Binary(String value) {
		if(isValidFormat(value)) this.value = value;
		else throw new NumberFormatException();
	}
	
	public Binary(int value) {
		this(String.valueOf(value));
	}
	
	public Binary(long value) {
		this(String.valueOf(value));
	}
	
	public Binary(float value) {
		this(String.valueOf(value));
	}
	
	public Binary(double value) {
		this(String.valueOf(value));
	}
	
	public static boolean isValidFormat(String o) {
		char[] ch = o.toCharArray();
		
		for(char caracter : ch)
			if(caracter != '0' && caracter != '1')
				return false;
		
		return true;
		
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public int toInt() {
		int[] nums = getNumber();
		int n = 0;
		
		for(int i = 0, j = nums.length - 1; i < nums.length; i++, j--)
			n += nums[i] * Math.pow(2, j);
		
		return n;
	}
	
	public long toLong() {
		int[] nums = getNumber();
		long n = 0;
		
		for(int i = 0, j = nums.length - 1; i < nums.length; i++, j--)
			n += nums[i] * Math.pow(2, j);
		
		return n;
	}
	
	public int[] getNumber() {
		int[] nums = new int[value.length()];
		for(int i = 0; i < value.length(); i++)
			nums[i] = Integer.valueOf(new String(Character.toString(value.toCharArray()[i])));
		
		return nums;
	}

}
