package net.argus.cjson.value;

import java.util.List;

public class CJSONString extends CJSONValue {
	
	private String value;
	
	public CJSONString() {}
	public CJSONString(String value) {this.value = value;}
	
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
	
	public void setValue(String value) {this.value = value;}
	
	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "\"" + value + "\"";
	}

}
