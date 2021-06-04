package net.argus.cjson.value;

import java.util.List;

import net.argus.exception.CJSONException;

public class CJSONNull extends CJSONValue {
	
	public static CJSONNull nextNull(List<Character> chars) {
		for(int i = 0; i < 4; i++)
			chars.remove(0);
		
		char next = chars.get(0);
		if(next != ',' && next != '}' && next != ']')
			throw new CJSONException("\"null\" syntax error");
		
		return new CJSONNull();
	}
	
	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public String toString() {
		return null;
	}

}
