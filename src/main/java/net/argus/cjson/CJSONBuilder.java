package net.argus.cjson;

import net.argus.cjson.value.CJSONBoolean;
import net.argus.cjson.value.CJSONInteger;
import net.argus.cjson.value.CJSONNull;
import net.argus.cjson.value.CJSONObject;
import net.argus.cjson.value.CJSONString;
import net.argus.cjson.value.CJSONValue;

public class CJSONBuilder {
	
	private CJSONObject main = new CJSONObject();
	
	public CJSONBuilder() {}
	public CJSONBuilder(CJSONObject main) {this.main = main;}
	
	public void addItem(String path, CJSONItem item) {
		CJSONObject parent = main;
		for(String name : path.split("\\.")) {
			if(parent.getValue(name) == null)
				parent.addItem(name, new CJSONObject());
			parent = parent.getObject(name);
		}
		parent.addItem(item);
	}
	
	public void addValue(String path, CJSONString name, CJSONValue value) {
		addItem(path, new CJSONItem(name, value));
	}
	
	public void addValue(String path, String name, CJSONValue value) {
		addValue(path, new CJSONString(name), value);
	}
	
	public void addObject(String path, String name, CJSONObject obj) {
		addValue(path, new CJSONString(name), obj);
	}
	
	public void addString(String path, String name, String value) {
		addValue(path, new CJSONString(name), new CJSONString(value));
	}
	
	public void addBoolean(String path, String name, boolean value) {
		addValue(path, new CJSONString(name), new CJSONBoolean(value));
	}
	
	public void addInt(String path, String name, int value) {
		addValue(path, new CJSONString(name), new CJSONInteger(value));
	}
	
	public void addNull(String path, String name) {
		addValue(path, new CJSONString(name), new CJSONNull());
	}
	
	public CJSONObject getMainObject() {return main;}
	
	@Override
	public String toString() {
		return "CJSONBuilder@[" + main.toString() + "]";
	}

}
