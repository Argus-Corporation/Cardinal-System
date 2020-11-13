package net.argus.file.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;

public class CJSONObject {
	
	private CJSONString name;
	private List<CJSONItem> items = new ArrayList<CJSONItem>();
	private List<CJSONItemArray> arrays = new ArrayList<CJSONItemArray>();
	
	public CJSONObject(CJSONString name, List<CJSONItem> items, List<CJSONItemArray> arrays) {
		this.name = name;
		this.items = items;
		this.arrays = arrays;
	}
	
	public CJSONObject() {this(null, null, null);}
	
	public static CJSONObject nextObject(char[] chars) {
		List<CJSONItem> items = new ArrayList<CJSONItem>();
		List<CJSONItemArray> arrays = new ArrayList<CJSONItemArray>();
		CJSONObject obj = new CJSONObject();
		
		obj.setName(CJSONString.nextString(chars));
		chars = ArrayManager.remove(chars, ArrayManager.indexOf(chars, '{'), chars.length);
		
		chars = CJSONPareser.removeNextObject(chars);
		
		while(chars[0] != '}') {
			CJSONString name = CJSONString.nextString(chars);
			chars = ArrayManager.remove(chars, ArrayManager.indexOf(chars, ':') + 1, chars.length);
			
			if(chars[0] == '[') {
				CJSONItemArray array = CJSONItemArray.nextArray(chars);
				array.setName(name);
				
				arrays.add(array);
				chars = CJSONPareser.removeFirstArray(chars);
			}else {
				CJSONItem item = CJSONItem.nextItem(chars);
				item.setName(name);
				
				items.add(item);
				int index = ArrayManager.indexOf(chars, ',') + 1;
				chars = ArrayManager.remove(chars, index!=0?index:ArrayManager.indexOf(chars, '}'), chars.length);
			}
		}
		
		obj.setItems(items);
		obj.setArray(arrays);
		return obj;
	}
	
	public CJSONString getName() {return name;}
	public List<CJSONItem> getItems() {return items;}
	public CJSONItem getItem(int index) {return items.get(index);}
	
	public List<CJSONItemArray> getArray() {return arrays;}
	public CJSONItemArray getArray(int index) {return arrays.get(index);}
	
	public CJSONObject getValue(String name) {return getValue(new CJSONString(name));}
	
	public CJSONObject getValue(CJSONString name) {
		for(CJSONItem obj : items) {
			if(obj.getName().equals(name))
				return obj.getValue();
		}
		return null;
	}
	
	public CJSONObject[] getArrayValue(String arrayName) {return getArrayValue(new CJSONString(arrayName));}
	
	public CJSONObject[] getArrayValue(CJSONString name) {
		for(CJSONItemArray obj : arrays) {
			//System.out.println(obj.getName());
			if(obj.getName().equals(name))
				return obj.getValues();
		}
		return null;
	}
	
	public void setItems(List<CJSONItem> items) {this.items = items;}
	public void setArray(List<CJSONItemArray> arrays) {this.arrays = arrays;}
	public void setName(CJSONString name) {this.name = name;}
	

}
