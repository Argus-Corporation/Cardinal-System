package net.argus.cjson.value;

import java.util.ArrayList;
import java.util.List;

import net.argus.cjson.Array;
import net.argus.util.ArrayManager;

public class CJSONArray extends CJSONValue {
	
	private final Array array = new Array();
	
	public CJSONArray() {}
	public CJSONArray(List<CJSONValue> array) {this.array.setArray(array);}
	public CJSONArray(CJSONValue[] array) {this.array.setArray(ArrayManager.convert(array));}
	
	public static CJSONArray nextArray(List<Character> chars) {
		List<CJSONValue> values = new ArrayList<CJSONValue>();
		
		chars.remove(0);   // remove "["
		if(chars.get(0) != ']')
			values.add(CJSONValue.nextValue(chars));

		while(chars.get(0) == ',') {
			chars.remove(0);
			values.add(CJSONValue.nextValue(chars));
		}
		chars.remove(0); // remove "]"
		return new CJSONArray(values);
	}
	
	@Override
	public Array getValue() {
		return array;
	}

	@Override
	public String toString() {
		String str = "[";
		for(int i = 0; i < array.length() - 1; i++)
			str += array.get(i).toString() + ", ";
		
		if(array.length() > 0)
			str += array.get(array.length() - 1);
		
		str += "]";
		
		return str;
	}

}
