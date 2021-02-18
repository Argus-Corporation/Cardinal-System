package net.argus.file.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;

@Deprecated
public class CJSONItemArray {
	
	private CJSONString name;
	private CJSONObject[] values;
	
	public CJSONItemArray(CJSONString name, CJSONObject[] value) {
		this.name = name;
		this.values = value;
	}
	
	public CJSONItemArray(String name, CJSONObject[] value) {
		this(new CJSONString(name), value);
	}
	
	public CJSONItemArray(String name, String[] value) {
		this(name, valueOf(value));
	}
	
	public CJSONItemArray(String name, byte[] value) {
		this(name, valueOf(value));
	}
	
	public CJSONItemArray() {}
	
	public static CJSONItemArray nextArray(char[] chars) {
		CJSONItemArray itemArray = new CJSONItemArray();
		List<CJSONObject> items = new ArrayList<CJSONObject>();
		
		chars = CJSONPareser.removeNextArray(chars);
		chars = ArrayManager.remove(chars, 0);
		chars = ArrayManager.remove(chars, chars.length - 1);
		
		while(chars.length != 0) {
			items.add(CJSONItem.nextItem(chars).getValue());
			int index = ArrayManager.indexOf(chars, ',') + 1;
			chars = ArrayManager.remove(chars, index!=0?index:chars.length, chars.length);
		}
		itemArray.setValues((CJSONObject[]) items.toArray(new CJSONObject[items.size()]));
		return itemArray;
	}
	
	private static CJSONObject[] valueOf(byte[] value) {
		CJSONObject[] objValue = new CJSONObject[value.length];
		for(int i = 0; i < value.length; i++)
			objValue[i] = new CJSONString(String.valueOf(value[i]));
		
		return objValue;
	}
	
	private static CJSONObject[] valueOf(String[] value) {
		CJSONObject[] objValue = new CJSONObject[value.length];
		for(int i = 0; i < value.length; i++)
			objValue[i] = new CJSONString(value[i]);
		
		return objValue;
	}
	
	public CJSONString getName() {return name;}
	public CJSONObject[] getValues() {return values;}
	
	public byte[] getByte() {
		byte[] data = new byte[values.length];
		for(int i = 0; i < values.length; i++)
			data[i] = Byte.valueOf(values[i].toString());
		return data;
	}

	public void setValues(CJSONObject[] values) {this.values = values;}
	public void setName(CJSONString name) {this.name = name;}
	
	@Override
	public String toString() {
		String txt = "[name " + name + "] [values ";
		
		for(int i= 0; i < values.length - 1; i++)
			txt += values[i] + ", ";
		txt += values[values.length - 1] + "]";
			
		return txt;
	}

}
