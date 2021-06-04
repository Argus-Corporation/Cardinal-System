package net.argus.cjson;

import java.util.List;

import net.argus.cjson.value.CJSONString;
import net.argus.cjson.value.CJSONValue;

public class CJSONItem {
	
	private CJSONString name;
	private CJSONValue value;
	
	public CJSONItem() {}
	
	public CJSONItem(String name, CJSONValue value) {
		this(new CJSONString(name), value);
	}
	
	public CJSONItem(CJSONString name, CJSONValue value) {
		this.name = name;
		this.value = value;
	}
	
	public static CJSONItem nextItem(List<Character> chars) {
		CJSONString name = CJSONString.nextString(chars);
		chars.remove(0); //remove ":"

		CJSONValue value = CJSONValue.nextValue(chars);
		
		return new CJSONItem(name, value);
	}
	
	public String getName() {return name.getValue();}
	public CJSONString getCJSONName() {return name;}
	public CJSONValue getValue() {return value;}
	
	public void setName(CJSONString name) {this.name = name;}
	public void setValue(CJSONValue value) {this.value = value;}
	
	@Override
	public String toString() {
		return name.toString() + ": " + value.toString();
	}

}
