package net.argus.cjson.value;

import java.util.List;

import net.argus.cjson.Array;
import net.argus.cjson.CJSONItem;
import net.argus.exception.CJSONException;
import net.argus.util.StringManager;

public abstract class CJSONValue {
	
	public static CJSONValue nextValue(List<Character> chars) {
		switch(nextType(chars)) {
			case OBJECT:
				return CJSONObject.nextObject(chars);
				
			case ARRAY:
				return CJSONArray.nextArray(chars);
				
			case BOOLEAN:
				return CJSONBoolean.nextBoolean(chars);
				
			case INTEGER:
				return CJSONInteger.nextInteger(chars);
				
			case STRING:
				return CJSONString.nextString(chars);
				
			case NULL:
				return CJSONNull.nextNull(chars);
		}
		return null;
	}
	
	public static CJSONType nextType(List<Character> chars) {
		if(chars.get(0) == '{')
			return CJSONType.OBJECT;
		if(chars.get(0) == '[')
			return CJSONType.ARRAY;
		if(chars.get(0) == '"')
			return CJSONType.STRING;

		if(StringManager.convert(chars).startsWith("true") || StringManager.convert(chars).startsWith("false"))
			return CJSONType.BOOLEAN;
		
		if(StringManager.isInteger(Character.toString(chars.get(0))) || chars.get(0) == '-')
			return CJSONType.INTEGER;
		
		if(StringManager.convert(chars).startsWith("null"))
			return CJSONType.NULL;

		throw new CJSONException("syntax error");
	}
	
	public CJSONValue getArrayValue(int index) {
		return getArray().get(index);
	}
	
	public Array getArray() {
		Object obj = getValue();
		if(obj instanceof Array)
			return (Array) obj;
		
		if(obj == null)
			return null;
			
		throw new CJSONException("this object is not an Array");
	}
	
	public CJSONObject getObject() {
		if(this instanceof CJSONObject)
			return (CJSONObject) this;
		
		throw new CJSONException("this object is not an Object");
	}
	
	public String getString() {
		Object obj = getValue();
		if(obj instanceof String)
			return obj.toString();

		if(obj == null)
			return null;
		
		throw new CJSONException("this object is not a String");
	}
	
	public boolean getBoolean() {
		Object obj = getValue();
		if(obj instanceof Boolean)
			return Boolean.valueOf(obj.toString());
		
		throw new CJSONException("this object is not a boolean");
	}
	
	public int getInt() {
		Object obj = getValue();
		if(obj instanceof Integer)
			return Integer.valueOf(obj.toString());
		
		throw new CJSONException("this object is not an int");
	}
	
	public CJSONValue getArrayValue(String name, int index) {
		return getValue(name).getArrayValue(index);
	}
	
	public Array getArrayArrayValue(String name, int index) {
		return getValue(name).getArrayValue(index).getArray();
	}
	
	public String getArrayStringValue(String name, int index) {
		return getValue(name).getArrayValue(index).getString();
	}
	
	public boolean getArrayBooelanValue(String name, int index) {
		return getValue(name).getArrayValue(index).getBoolean();
	}
	
	public int getArrayIntValue(String name, int index) {
		return getValue(name).getArrayValue(index).getInt();
	}
	
	public Array getArray(String name) {
		return getValue(name).getArray();
	}
	
	public CJSONObject getObject(String name) {
		return getValue(name).getObject();
	}
	
	public String getString(String name) {
		return getValue(name).getString();
	}
	
	public boolean getBoolean(String name) {
		return getValue(name).getBoolean();
	}
	
	public int getInt(String name) {
		return getValue(name).getInt();
	}
	
	@SuppressWarnings("unchecked")
	public CJSONValue getValue(String name) {
		Object obj = getValue();
		if(obj instanceof List) {
			List<CJSONItem> items = (List<CJSONItem>) obj;
			for(CJSONItem item : items) {
				if(item.getName().equals(name))
					return item.getValue();
			}
			
			return null;
		}
		throw new CJSONException("illegal request");
	}
	
	public abstract Object getValue();
	
	@Override
	public abstract String toString();

}
