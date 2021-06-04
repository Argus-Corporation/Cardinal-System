package net.argus.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.cjson.value.CJSONValue;

public class Array {
	
	private List<CJSONValue> array = new ArrayList<CJSONValue>();
	
	public Array() {}
	public Array(List<CJSONValue> array) {this.array = array;}
	
	public int length() {return array.size();}
	
	public void setArray(List<CJSONValue> array) {this.array = array;}
	public List<CJSONValue> getArray() {return array;}
	
	public void addValue(CJSONValue value) {array.add(value);}
	public CJSONValue get(int index) {return array.get(index);}
	
	@Override
	public String toString() {
		return array.toString();
	}
	
}
