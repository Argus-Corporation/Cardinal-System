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

	public void addValue(String key, String value) {values.put(key, value);}
	public void addValue(DoubleStock<String, String> values) {addValue(values.getFirst(), values.getSecond());}
	
	@Override
	public String toString() {
		return values.toString();
	}

}
