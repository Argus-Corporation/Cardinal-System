package net.argus.net.pack.key;

import java.util.ArrayList;
import java.util.List;

public class PackageKey {
	
	public static final EndKey END_KEY = new EndKey();
	
	private String name;
	private Object value[];
	
	public PackageKey(String name, Object value) {
		this.name = name;
		this.value = new Object[] {value};
	}
	
	public PackageKey(String name, Object[] value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {return name;}
	public Object getValue() {
		try{return value[0];}
		catch (ArrayIndexOutOfBoundsException e) {return null;}
	}

	public Object[] getValues() {return value;}
	
	public boolean isValueNull() {
		for(Object obj : value) {
			if(obj == null || obj.toString().equals(""))
				return true;
		}
		return false;
	}
	
	public static List<PackageKey> getKeys(List<String> lines) {
		List<PackageKey> keys = new ArrayList<PackageKey>();
		for(; 0 < lines.size(); lines.remove(0)) {
			String line = lines.get(0);
			
			if(line.equals(""))
				continue;
			
			String name = getName(line);
			if(name.contains("["))
				keys.add(ArrayKey.getArrayKey(name, lines));
			else
				keys.add(PackageKey.getKey(name, line));
			
		}

		return keys;
	}
	
	public static PackageKey getKey(String name, String line) {
		return new PackageKey(name, getValue(line));
	}
	
	public static String getName(String line) {
		return line.substring(0, line.indexOf(':'));
	}
	
	public static String getValue(String line) {
		return line.substring(line.indexOf(':') + 2);
	}
	
	@Override
	public String toString() {
		return name + ": " + getValue();
	}

}
