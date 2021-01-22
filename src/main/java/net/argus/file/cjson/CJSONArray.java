package net.argus.file.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;

public class CJSONArray extends CJSONObject {
	
	private CJSONString name;
	private CJSONObject[] values;
	
	public CJSONArray(CJSONString name, CJSONObject[] value) {
		this.values = value;
		this.name = name;
	}
	
	public CJSONArray(String name, CJSONObject[] value) {
		
	}
	
	public CJSONArray(CJSONString name, String[] value) {
		this(name, valueOf(value));
	}
	
	public CJSONArray(String name, String[] value) {
		this(new CJSONString(name), valueOf(value));
	}
	
	public CJSONArray(String name, byte[] value) {
		this(new CJSONString(name), valueOf(value));
	}
	
	public CJSONArray() {}
	
	public static CJSONArray nextArray(char[] chars) {
		CJSONArray array = new CJSONArray();
		List<CJSONObject> items = new ArrayList<CJSONObject>();
		
		chars = ArrayManager.remove(chars, 0);
		
		while(chars.length != 1) {
			items.add(CJSONItem.nextElement(chars).getValue());
			
			if((items.get(items.size()-1) instanceof CJSONObject) && !(items.get(items.size()-1) instanceof CJSONElement))
				chars = CJSONPareser.removeFirstItem(chars);
			else if(!(items.get(items.size()-1) instanceof CJSONElement))
				chars = CJSONPareser.removeFirstArray(chars);
			
			
			int index = ArrayManager.indexOf(chars, ',') + 1;
			int indexCro = ArrayManager.indexOf(chars, ']') + 1;

			if(indexCro > index)
				if(index != 0)
					chars = ArrayManager.remove(chars, index, chars.length);
				else
					chars = ArrayManager.remove(chars, ArrayManager.indexOf(chars, ']')+1, chars.length);
			else 
				chars = new char[1];
			
		}
		array.setValues((CJSONObject[]) items.toArray(new CJSONObject[items.size()]));
		return array;
		
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

	public void setName(CJSONString name) {this.name = name;}
	public void setValues(CJSONObject[] values) {this.values = values;}

}
