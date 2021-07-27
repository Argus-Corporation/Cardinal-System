package net.argus.lang;

import java.util.HashMap;
import java.util.Map;

import net.argus.util.DoubleStock;

public class LangValues {
	
	private Map<String, String> values = new HashMap<String, String>();
	
	public LangValues() {}

	public String getValue(String key) {
		String value = values.get(key);
		return value!=null?value:key;
	}
	
	public Map<String, String> getValues() {return values;}

	public LangValues putValue(String key, String value) {values.put(key, value); return this;}
	public LangValues putValue(DoubleStock<String, String> values) {return putValue(values.getFirst(), values.getSecond());}
	
	@Override
	public String toString() {
		return values.toString();
	}

}
