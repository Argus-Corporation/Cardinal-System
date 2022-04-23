package net.argus.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringManager {
	
	public static boolean isInteger(String str) {
		try {Integer.valueOf(str);}
		catch(NumberFormatException e) {return false;}
		return true;
	}
	
	public static boolean isFloat(String str) {
		try {Float.valueOf(str);}
		catch(NumberFormatException e) {return false;}
		return true;
	}
	
	public static boolean isBoolean(String str) {
		if(str.equals("true") || str.equals("false"))
			return true;
		return false;
	}
	
	public static String convert(List<Character> chars) {
		String str = "";
		for(char c : chars)
			str += c;
		
		return str;
	}
	
	public static String[] valueOf(String[] strs) {
		boolean inStr = false;
		List<String> list = new ArrayList<String>();
		String inValue = "";
		
		for(String str : strs) {
			char[] chars = str.toCharArray();
			String value = "";
			
			for(int i = 0; i < chars.length; i++) {
				if(chars[i] == '\\')
					i += 2;
				
				if(!inStr && chars[i] == '\"') {
					inStr = true;
					i++;
				}
				
				if(inStr && chars[i] == '\"') {
					inStr = false;
					break;
				}
				
				if(inStr)
					inValue += chars[i];
				else
					value += chars[i];
				
			}
			if(inStr)
				inValue += " ";
			else if(!inValue.equals("")) {
				list.add(inValue);
				inValue = "";
			}else if(!value.equals("")) {
				list.add(value);
				value = "";
			}
		}
		
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	public static String secureString(String str) {
		char[] chars = str.toCharArray();
		String value = "";
		
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == '\"') {
				value += "\\";
			}
			value += chars[i];
		}
		return value;
	}
		
	public static Integer[] indexOf(String str, char car) {
		List<Integer> ints = new ArrayList<Integer>();
		char[] chars = str.toCharArray();
		 
		for(int i = 0; i < chars.length; i++)
			if(chars[i] == car)
				ints.add(i);
		
		return (Integer[]) ints.toArray(new Integer[ints.size()]);
	}
	
	/*public static String[] split(String str, char regex) {
		List<String> splited = new ArrayList<String>();
		char[] chars = str.toCharArray();
		
		String spl = "";
		for(char car : chars) {
			if(car == regex)
				splited.add(spl);
			spl += car;
		}
		
		return (String[]) splited.toArray(new String[splited.size()])
	}*/
	
	public static String remplace(String str, int pos, int length, String newValue) {
		char[] chars = str.toCharArray();
		char[] value = newValue.toCharArray();
		
		chars = Arrays.copyOf(chars, chars.length + value.length - length);
		for(int i = 0; i < value.length; i++)
			chars[pos + i] = value[i];
		
		for(int i = pos + value.length; i < chars.length; i++) {
			chars[i] = str.charAt((i - value.length) + length);
		}
		
		return new String(chars);
	}
	
	public static String remplace(String str, String old, String newStr) {
		if(str == null)
			return str;
		
		for(boolean valid = false; !valid;) {
			if(str.contains(old))
				str = valueOf(str, old, newStr);
			else
				valid = true;
		}
				
		return str;
	}
	
	private static String valueOf(String str, String old, String add) {
		String first = str.substring(0, str.indexOf(old));
		String second = str.substring(str.indexOf(old) + 1);

		return first + add + second;
	}
	
	public static String remove(String str, char rem) {
		char[] chars = str.toCharArray();
		String value = "";
		
		for(int i = 0; i < chars.length; i++)
			if(chars[i] != rem)
				value += chars[i];
		
		return value;
	}
	
	public static <T> String toString(T[] array) {
		String str = "";
		for(T t : array)
			str += t.toString();
		
		return str;
	}
	
	public static void main(String[] args) {
		//String enter = "\"hello comment allez vous\"+%test%+\"hello\"";
		//System.out.println(remplace(enter, 27, 6, "12"/*25*/));
		/*for(String str : valueOf(enter.split(" ")))
			System.out.println(str);*/
	}

}
