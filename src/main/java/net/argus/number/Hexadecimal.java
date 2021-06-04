package net.argus.number;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.Math;

public class Hexadecimal {
	
	private static final char[] character = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', '-'};
	
	public enum CharHex {
		
		a0(0, '0'), a1(1, '1'), a2(2, '2'), a3(3, '3'), a4(4, '4'),
		a5(5, '5'), a6(6, '6'), a7(7, '7'), a8(8, '8'), a9(9, '9'),
		A(10, 'A'), B(11, 'B'), C(12, 'C'), D(13, 'D'), E(14, 'E'), F(15, 'F');
		
		int value;
		char ch;
		
		CharHex(int value, char ch) {
			this.value = value;
			this.ch = ch;
		}
		
		public static int getValue(char ch) {
			for(CharHex chx : CharHex.values())
				if(chx.ch == ch) return chx.value;
			
			return 0;
		}
		
		public static char getChar(int value) {
			for(CharHex chx : CharHex.values())
				if(chx.value == value) return chx.ch;
			
			return '0';
		}
	}
	
	private String value;
	
	public Hexadecimal(String value) {
		if(isValidFormat(value)) this.value = value.toUpperCase();
		else throw new NumberFormatException();
	}
	
	public static boolean isValidFormat(String value) {
		value = value.toUpperCase();
		char[] chs = value.toCharArray();
		
		for(char ch : chs) {
			boolean valid = false;
			for(int i = 0; i < character.length; i++)
				if(ch == character[i]) valid = true;
			
			if(!valid) return false;
		}
		
		return true;
	}
	
	public static Hexadecimal valueOf(int n) {
		List<Character> hex = new ArrayList<Character>();
		
		String hexs = n < 0?"-":"";
		if(n < 0)
			n = -n;
		
		long n0 = n;

		do {
			hex.add(CharHex.getChar((int) (n0%16)));
			n0 = n0 / 16;
		}while(n0 > 0);
		
		for(int i = hex.size() - 1; i >= 0; i--)
			hexs += hex.get(i);
		
		return new Hexadecimal(hexs);
	}
	
	@Override
	public String toString() {return value;}
	
	public int toInt() {
		char[] chs = value.toCharArray();
		int n = 0;
			
			
		for(int i = 0, j = chs.length - 1; i < chs.length; i++, j--) {
			int val = CharHex.getValue(chs[i]);
			n += val * Math.pow(16, j);
		}
		if(chs[0] == '-')
			n = -n;
		
		return n;
	}
	
	public long toLong() {
		char[] chs = value.toCharArray();
		long n = 0;
		
		for(int i = 0, j = chs.length - 1; i < chs.length; i++, j--) {
			int val = CharHex.getValue(chs[i]);
			n += val * Math.pow(16, j);
		}
		
		if(chs[0] == '-')
			n = -n;
		
		return n;
	}
	
	public Binary toBinary() {return Math.toBinary(toInt());}
	
}
