package net.argus.file.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;

public class CJSONObject {
	
	private CJSONString name;
	private List<CJSONItem> items = new ArrayList<CJSONItem>();
	private List<CJSONArray> arrays = new ArrayList<CJSONArray>();
	
	public CJSONObject(List<CJSONItem> items, List<CJSONArray> arrays) {
		this.items = items;
		this.arrays = arrays;
	}
	
	public CJSONObject(CJSONString name) {
		this.name = name;
	}
	
	public CJSONObject(String name) {
		this(new CJSONString(name));
	}
	
	public CJSONObject() {}
	
	public static CJSONObject nextObject(char[] chars) {
		List<CJSONItem> items = new ArrayList<CJSONItem>();
		List<CJSONArray> arrays = new ArrayList<CJSONArray>();
		CJSONObject obj = new CJSONObject();
		
		while(ArrayManager.isExist(chars, 0) && chars[0] != '}') {
			if(chars[0] == ',')
				chars = ArrayManager.remove(chars, 0);
				
			CJSONItem item = CJSONItem.nextItem(chars);
			if(item.getValue() instanceof CJSONArray) {
				CJSONArray array = (CJSONArray) item.getValue();
				array.setName(item.getName());
				
				arrays.add(array);
			}else
				items.add(CJSONItem.nextItem(chars));
			
			if(!(items.get(items.size()-1).getValue() instanceof CJSONElement)) {
				chars = ArrayManager.remove(chars, 1, chars.length);
				chars = CJSONPareser.removeFirstItem(chars);
			}else {
				int index = ArrayManager.indexOf(chars, ',') + 1;
				if(index != 0)
					chars = ArrayManager.remove(chars, index, chars.length);
				else
					chars = ArrayManager.remove(chars, ArrayManager.indexOf(chars, '}'), chars.length);
			}
		}

		obj.setArray(arrays);
		obj.setItems(items);
		
		return obj;
	}
	
	public CJSONString getName() {return name;}
	public List<CJSONItem> getItems() {return items;}
	public CJSONItem getItem(int index) {return items.get(index);}
	
	public List<CJSONArray> getArray() {return arrays;}
	public CJSONArray getArray(int index) {return arrays.get(index);}
	
	public CJSONObject getValue(String name) {return getValue(new CJSONString(name));}
	
	public CJSONObject getValue(CJSONString name) {
		for(CJSONItem obj : items) {
			if(obj.getName().equals(name))
				return obj.getValue();
		}
		return null;
	}
	
	public CJSONObject getValue(int index) {return items.get(index).getValue();}
	
	public CJSONObject[] getArrayValue(String arrayName) {return getArrayValue(new CJSONString(arrayName));}
	
	public byte[] getByte(String arrayName) {return getByte(new CJSONString(arrayName));}
	
	public CJSONObject[] getArrayValue(CJSONString name) {
		for(CJSONArray obj : arrays) {
			if(obj.getName().equals(name))
				return obj.getValues();
		}
		return null;
	}
	
	public byte[] getByte(CJSONString name) {
		for(CJSONArray obj : arrays) {
			if(obj.getName().equals(name))
				return obj.getByte();
		}
		return null;
	}
	
	public int size() {return items.size();}
	
	public void setItems(List<CJSONItem> items) {this.items = items;}
	public void setArray(List<CJSONArray> arrays) {this.arrays = arrays;}
	public void setName(CJSONString name) {this.name = name;}

	public void addItem(CJSONItem item) {items.add(item);}
	public void addItemArray(CJSONArray array) {arrays.add(array);}
	
}
