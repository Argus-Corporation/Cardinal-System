package net.argus.file.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;
import net.argus.util.CharacterManager;
import net.argus.util.StringManager;

@Deprecated
public class CJSONBuilder {
	
	protected List<CJSONItem> items = new ArrayList<CJSONItem>();
	protected String file;
	
	public CJSONBuilder() {}
	
	public CJSONBuilder(CJSON cjson) {
		for(int i = 0 ; i < cjson.size(); i++) {
			addItem(cjson.getItem(i));
		}
	}
	
	public void addItem(CJSONItem item) {items.add(item);}
	
	public CJSONItem getItem(int index) {return items.get(index);}
	
	public CJSONBuilder build() {
		file = "";
		
		for(CJSONItem mainItem : items) {
			file += nextItem(mainItem);
		}
		
		if(ArrayManager.isExist(file.toCharArray(), file.length() - 2))
			file = file.substring(0, file.length() - 2);
		
		return this;
	}
	
	protected String nextObject(CJSONObject mainObject) {
		String file = "";
		if(isObject(mainObject)) {
			file += "{";
			for(int i = 0; i < mainObject.size(); i++)
				file += nextItem(mainObject.getItem(i));
			
			for(int i = 0; i < mainObject.arraySize(); i++)
				file += nextArray(mainObject.getArray(i));
			
			if(ArrayManager.isExist(file.toCharArray(), file.length() - 2))
				file = file.substring(0, file.length() - 2);
			
			file += "} ";
		}else
			if(!mainObject.toString().equals("") && CharacterManager.isNumber(mainObject.toString().charAt(0)) ||
					CharacterManager.isBoolean(mainObject.toString()))
				file += mainObject;
			else
				file += "\"" +  StringManager.secureString(mainObject.toString()) + "\"";
				
		file += ", ";
		
		return file;
		
	}
	
	protected String nextItem(CJSONItem cjsonItem) {
		String file = "";
		String obj = "";
		
		file += "\"" + cjsonItem.getName() + "\": ";
		
		obj = nextObject(cjsonItem.getValue());
		
		/*
		*/
		file += obj;
		
		return file;
	}
	
	protected String nextArray(CJSONArray cjsonArray) {
		String file = "";
		file += "\"" + cjsonArray.getName() + "\": [";
		for(CJSONObject obj : cjsonArray.getValues())
			file += nextObject(obj);
		
		if(!file.equals(""))
			file = file.substring(0, file.length() - 2);
		
		file += "], ";
		return file;
	}
	
	protected boolean isObject(CJSONObject obj) {
		return !(obj instanceof CJSONElement);
	}
	
	public String getFile() {return file;}
}
