package net.argus.beta.net.pack;

import net.argus.cjson.value.CJSONValue;

public class PackageEntry {
	
	private String name;
	private Object value;
	
	public PackageEntry(String name, Object value) {
		this.name = name;
		this.value = value;
		
		if(value == null)
			return;
		
		if(!(value instanceof Integer) && !(value instanceof String) && !(value instanceof Boolean) && !(value instanceof CJSONValue[]))
			throw new IllegalArgumentException();
		
	}
	
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return name + " = " + value;
	}
	
}
