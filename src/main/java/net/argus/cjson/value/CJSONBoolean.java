package net.argus.cjson.value;

import java.util.List;

import net.argus.exception.CJSONException;

public class CJSONBoolean extends CJSONValue {
	
	private boolean value;
	
	public CJSONBoolean() {}
	public CJSONBoolean(boolean value) {this.value = value;}
	
	public static CJSONBoolean nextBoolean(List<Character> chars) {
		boolean value = chars.get(0).equals('t');
		
		int rm;
		if(value)
			rm = 4;
		else
			rm = 5;
		
		for(int i = 0; i < rm; i++)
			chars.remove(0);
		
		char next = chars.get(0);
		if(next != ',' && next != '}' && next != ']')
			throw new CJSONException("boolean syntax error");
		
		return new CJSONBoolean(value);
	}
	
	@Override
	public Boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

}
