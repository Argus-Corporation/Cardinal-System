package net.argus.file.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.CharacterManager;
import net.argus.util.StringManager;

public class CJSONBuilder {
	
	protected List<CJSONObject> objs = new ArrayList<CJSONObject>();
	protected String file;
	
	
	public void addObject(CJSONObject obj) {objs.add(obj);}
	
	public CJSONObject getObject(int index) {return objs.get(index);}
	
	public CJSONBuilder build() {
		file = "";
		
		for(CJSONObject mainObj :  objs) {
			file += "\"" + mainObj.getName() + "\": {";
			file += nextObject(mainObj);
			file += "} ";
		}
		
		return this;
	}
	
	protected String nextObject(CJSONObject mainObj) {
		String file = "";
		String item = "";
		String array = "";
		
		item = nextItem(mainObj);
		array = nextItemArray(mainObj);
		
		if(array.equals("") && !item.equals(""))
			item = item.substring(0, item.length() - 2);
		
		file += item + array;
		return file;
	}
	
	protected String nextItem(CJSONObject parent) {
		String file = "";
		for(CJSONItem mainItem : parent.getItems()) {
			file += "\"" + mainItem.getName() + "\": ";
			if(isObject(mainItem.getValue())) {
				file += "{";
				file += nextObject(mainItem.getValue());
				file += "} ";
			}else
				if(!mainItem.getValue().toString().equals("") && CharacterManager.isNumber(mainItem.getValue().toString().charAt(0)) ||
						CharacterManager.isBoolean(mainItem.getValue().toString()))
					file += mainItem.getValue();
				else
					file += "\"" +  StringManager.secureString(mainItem.getValue().toString()) + "\"";
			
			file += ", ";
		}
		
		return file;
	}
	
	protected String nextItemArray(CJSONObject parent) {
		String file = "";
		
		for(CJSONItemArray mainArray : parent.getArray()) {
			file += "\"" + mainArray.getName() + "\": [";
			for(CJSONObject obj : mainArray.getValues()) {
				if(isObject(obj)) {
					file += "{";
					file += nextObject(obj);
					file += "}, ";
				}else if(CharacterManager.isNumber(obj.toString().charAt(0)) ||
						CharacterManager.isBoolean(obj.toString()))
					file += obj.toString() + ", ";
				else
					file += "\"" +  obj.toString() + "\", ";
			}
			if(!file.equals("")) {
				file = file.substring(0, file.length() - 2);
			}
			file += "], ";
		}
		
		if(!file.equals(""))
			file = file.substring(0, file.length() - 2);
		return file;
	}
	
	protected boolean isObject(CJSONObject obj) {
		try {obj.getItem(0);}
		catch (NullPointerException | IndexOutOfBoundsException e) {return false;}
		return true;
	}
	
	public String getFile() {return file;}
}
