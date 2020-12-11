package net.argus.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayManager<T> {
	
	public static char[] add(char[] array, char add, int pos) {
		char[] newArray = new char[array.length + 1];
		for(int i = 0; i < pos; i++) {
			newArray[i] = array[i];
		}
		newArray[pos] = add;
		for(int i = pos + 1; i < newArray.length; i++) {
			newArray[i] = array[i - 1];
		}
		return newArray;
	}
	
	public static char[] add(char[] array, char[] add) {
		char[] newArray = new char[array.length + add.length];
		for(int i = 0; i < array.length; i++)
			newArray[i] = array[i];
		for(int i = array.length; i < newArray.length; i++)
			newArray[i] = add[i - array.length];
		return newArray;
	}
	
	public static char[] remove(char[] array, int sub) {
		char[] newArray = new char[array.length - 1];
		
		for(int i = 0; i < sub; i++)
			newArray[i] = array[i];
		
		for(int i = sub; i < newArray.length; i++)
			newArray[i] = array[i + 1];
		
		return newArray;
	}
	
	public static char[] remove(char[] array, int min, int max) {
		char[] newArray = new char[max - min];
		
		for(int i = min, j = 0; j < newArray.length; i++, j++)
			newArray[j] = array[i];
		
		return newArray;
	}
	
	public static char[] remove(char[] array, char sub) {
		char[] newArray = new char[array.length - Counter.countOccurrences(new String(array), sub)];
		
		boolean valid = false;
		
		while(!valid) {
			valid = true;
			for(int i = 0; i < newArray.length; i++) {
				if(array[i] == sub) {
					array = remove(array, i);
					valid = false;
				}
				newArray[i] = array[i];
			}
		}
		
		return newArray;
	}
	
	public static String remplace(String str, char[] old, char newCar) {
		char[] chars = str.toCharArray();
		
		for(int i = 0; i < chars.length; i++)
			for(int j = 0; j < old.length; j++)
				if(chars[i] == old[j])
					chars[i] = newCar;
		
		return new String(chars);
	}
	
	public static String[] cut(String enter, char cut) throws ArrayStoreException {
		String[] word = new String[Counter.countOccurrences(enter, cut)];
		try {
			word[0] = enter.substring(0, enter.indexOf(cut));
			
			for(int i = 1; i < word.length - 1; i++) {
				enter = enter.substring(enter.indexOf(cut) + 1);
				word[i] = enter.substring(0, enter.indexOf(cut));
			}
			if(word.length > 1) {
				enter = enter.substring(enter.indexOf(cut) + 1);
				word[word.length - 1] = enter.substring(0, enter.indexOf(cut));
			}
		}catch(StringIndexOutOfBoundsException e) {
			throw new ArrayStoreException();
		}
		return word;
	}
	
	public static boolean isExist(Object[] array, int pos) {
		try {array[pos] = array[pos];}
		catch(ArrayIndexOutOfBoundsException e) {return false;}
		return true;
	}
	
	public static boolean isExist(int[] array, int pos) {
		try {array[pos] = array[pos];}
		catch(ArrayIndexOutOfBoundsException e) {return false;}
		return true;
	}
	
	public static boolean isExist(char[] array, int pos) {
		try {array[pos] = array[pos];}
		catch(ArrayIndexOutOfBoundsException e) {return false;}
		return true;
	}
	
	public List<T> convert(T[] array) {
		List<T> list = new ArrayList<T>();
		for(T t : array)
			list.add(t);
		return list;
	}
	
	public void add(List<T> list, T[] array) {
		for(T t : array) list.add(t);
	}
	
	public void add(List<T> list, List<T> array) {
		for(T t : array) list.add(t);
	}
	
	public static String[] convert(List<String> list) {
		String[] array = new String[list.size()];
		
		for(int i = 0; i < array.length; i++) array[i] = list.get(i);
		
		return array;
	}
	
	public static String convert(String[] array, int nul) {
		String result = "";
		
		for(String str : array)
			result += str + " ";
		result = result.substring(0, result.length() - 1);
	
		return result;
	}
	
	public static boolean content(Object[] array, Object cont) {
		for(Object obj : array)
			if(obj.equals(cont)) return true;
		
		return false;
	}
	
	public static int indexOf(char[] array, char index) {
		for(int i = 0; i < array.length; i++)
			if(array[i] == index)
				return i;
		return -1;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("gsdg");
		String[] test = new String[] {"lol", "gg", "bye"};
		
		new ArrayManager<String>().add(list, test);
		System.out.println(list);
	}

}
