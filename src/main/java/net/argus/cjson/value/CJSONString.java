package net.argus.cjson.value;

import java.util.List;

import net.argus.util.ArrayManager;

public class CJSONString extends CJSONValue {
	
	private String value;
	
	public CJSONString() {}
	public CJSONString(String value) {this.value = secureString(value);}
	
	public static CJSONString nextString(List<Character> chars) {
		chars.remove(0);
		String value = "";

		while(chars.get(0) != '"') {
			if(chars.get(0) == '\\' && chars.get(1) == '"') {
				value += '"';
				chars.remove(0);
				chars.remove(0);
				continue;
			}
			value += chars.get(0);
			chars.remove(0);
		}
		chars.remove(0);
		return new CJSONString(value);
	}
	
	private static String secureString(String value) {
		List<Character> charsVal = ArrayManager.toListChar(value.toCharArray());
		String ret = "";
		
		for(int i = 0; i < value.length(); i++) {
			char car = charsVal.get(i);
			if(car == '"' && charsVal.get(i-1) != '\\')
				ret += "\\";
			
			ret += car;
		}
		
		return ret;
	}
	
	private static String unsecureString(String value) {
		List<Character> charsVal = ArrayManager.toListChar(value.toCharArray());
		String ret = "";
		
		for(int i = 0; i < value.length(); i++) {
			char car = charsVal.get(i);
			if(car == '\\') {
				char next = charsVal.get(i+1);
				if(next == '"') {
					ret += next;
					i++;
					continue;
				}
			}
			
			ret += car;
		}
		
		return ret;
	}
	
	public void setValue(String value) {this.value = value;}
	
	@Override
	public String getValue() {
		return unsecureString(value);
	}

	@Override
	public String toString() {
		return "\"" + value + "\"";
	}

}
