package net.argus.cjson.value;

import java.util.List;

import net.argus.exception.CJSONException;
import net.argus.util.StringManager;

public class CJSONInteger extends CJSONValue {
	
	private int value;
	
	public CJSONInteger() {}
	public CJSONInteger(int value) {this.value = value;}
	
	public static CJSONInteger nextInteger(List<Character> chars) {
		String num = "";

		while(StringManager.isInteger(Character.toString(chars.get(0)))) {
			num += chars.get(0);
			chars.remove(0);
		}
		
		char next = chars.get(0);
		if(next != ',' && next != '}' && next != ']')
			throw new CJSONException("integer syntax error");
		
		return new CJSONInteger(Integer.valueOf(num));
	}
	
	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

}
