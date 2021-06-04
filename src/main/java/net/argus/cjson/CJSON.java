package net.argus.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.cjson.value.CJSONObject;
import net.argus.cjson.value.CJSONValue;

public class CJSON {
	
	private CJSONObject mainObject = new CJSONObject();
	
	public CJSON() {}
	public CJSON(CJSONBuilder builder) {this.mainObject = builder.getMainObject();}
	public CJSON(CJSONObject mainObject) {this.mainObject = mainObject;}
	
	public void addItem(CJSONItem item) {mainObject.addItem(item);}
	
	public CJSONObject getMainObject() {return mainObject;}
	
	public CJSONValue getArrayValue(String path, int index) {
		return getValue(path).getArrayValue(index);
	}
	
	public Array getArrayArrayValue(String path, int index) {
		return getValue(path).getArrayValue(index).getArray();
	}
	
	public String getArrayStringValue(String path, int index) {
		return getValue(path).getArrayValue(index).getString();
	}
	
	public boolean getArrayBooelanValue(String path, int index) {
		return getValue(path).getArrayValue(index).getBoolean();
	}
	
	public int getArrayIntValue(String path, int index) {
		return getValue(path).getArrayValue(index).getInt();
	}
	
	public Array[] getArrayArray(String path) {
		List<Array> list = new ArrayList<Array>();
		List<CJSONValue> values = getArray(path).getArray();
		
		for(CJSONValue v : values)
			list.add(v.getArray());
		
		return (Array[]) list.toArray(new Array[list.size()]);
	}
	
	public CJSONObject[] getObjectArray(String path) {
		List<CJSONObject> list = new ArrayList<CJSONObject>();
		List<CJSONValue> values = getArray(path).getArray();
		
		for(CJSONValue v : values)
			list.add(v.getObject());
		
		return (CJSONObject[]) list.toArray(new CJSONObject[list.size()]);
	}
	
	public String[] getStringArray(String path) {
		List<String> list = new ArrayList<String>();
		List<CJSONValue> values = getArray(path).getArray();
		
		for(CJSONValue v : values)
			list.add(v.getString());
		
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	public Integer[] getIntArray(String path) {
		List<Integer> list = new ArrayList<Integer>();
		List<CJSONValue> values = getArray(path).getArray();
		
		for(CJSONValue v : values)
			list.add(v.getInt());
		
		return (Integer[]) list.toArray(new Integer[list.size()]);
	}
	
	public Boolean[] getBooleanArray(String path) {
		List<Boolean> list = new ArrayList<Boolean>();
		List<CJSONValue> values = getArray(path).getArray();
		
		for(CJSONValue v : values)
			list.add(v.getBoolean());
		
		return (Boolean[]) list.toArray(new Boolean[list.size()]);
	}
	
	public Array getArray(String path) {
		return getValue(path).getArray();
	}
	
	public CJSONObject getObject(String path) {
		return getValue(path).getObject();
	}
	
	public String getString(String path) {
		return getValue(path).getString();
	}
	
	public boolean getBoolean(String path) {
		return getValue(path).getBoolean();
	}
	
	public int getInt(String path) {
		return getValue(path).getInt();
	}
	
	public CJSONValue getValue(String path) {
		CJSONValue value = mainObject;
		for(String name : path.split("\\."))
			value = value.getValue(name);
		return value;
	}
	
	@Override
	public String toString() {
		return mainObject.toString();
	}

}
