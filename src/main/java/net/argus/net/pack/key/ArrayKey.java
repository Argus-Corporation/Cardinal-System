package net.argus.net.pack.key;

import java.util.List;

public class ArrayKey extends PackageKey {

	public ArrayKey(String name, Object[] values) {
		super(name, values);
	}
	
	public static ArrayKey getArrayKey(String nameAndLength, List<String> lines) {
		String name = getName(nameAndLength);
		int length = getLength(nameAndLength);
		
		Object[] values = new Object[length];
		
		for(int i = 0; i < length; i++) {
			lines.remove(0);
			values[i] = lines.get(0);
		}
		
		return new ArrayKey(name, values);
	}
	
	public static String getName(String nameAndLength) {
		return nameAndLength.substring(0, nameAndLength.indexOf('['));
	}
	
	public static int getLength(String nameAndLength) {
		return Integer.valueOf(nameAndLength.substring(nameAndLength.indexOf('[') + 1, nameAndLength.indexOf(']')));

	}
	
	public int length() {return getValues().length;}
	
	@Override
	public String toString() {
		String s =  getName() + "[" + length() + "]:\r\n";
		for(Object v : getValues())
			s += v + "\r\n";
		
		s = s.substring(0, s.length() - 2);
		return s;
	}

}
