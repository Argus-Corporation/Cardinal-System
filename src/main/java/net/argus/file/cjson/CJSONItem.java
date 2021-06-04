package net.argus.file.cjson;

import net.argus.util.ArrayManager;

public class CJSONItem {
	
	private CJSONString name;
	private CJSONObject value;
	
	public CJSONItem(CJSONString name, CJSONObject value) {
		this.name = name;
		this.value = value;
	}
	
	public CJSONItem(String name, CJSONObject value) {
		this(new CJSONString(name), value);
	}
	
	public CJSONItem(String name, String value) {
		this(name, new CJSONString(value));
	}
	
	public CJSONItem(String name) {
		this(name, new String());
	}
	
	public CJSONItem() {this(null);}
	
	public static CJSONItem nextItem(char[] chars) {
		CJSONItem item = new CJSONItem();
		item.setName(CJSONString.nextString(chars));
		chars = ArrayManager.remove(chars, ArrayManager.indexOf(chars, ':')+1, chars.length);
		
		if(chars[0] != '[') 
			chars = CJSONPareser.removeNextItem(chars);
		
		
		item.setValue(nextElement(chars).getValue());

		return item;
	}
	
	public static CJSONItem nextElement(char[] chars) {
		CJSONItem item = new CJSONItem();
		switch(CJSONType.nextType(chars)) {
			case OBJECT:
				item.setValue(CJSONObject.nextObject(chars));
			//	System.out.println(CJSONType.OBJECT + "  " + item.getName() + "  " + item.getValue());
				break;
			case ARRAY:
				item.setValue(CJSONArray.nextArray(chars));
			//	System.out.println(CJSONType.ARRAY + "  " + item.getName() + "  " + item.getValue());
				break;
				
			case STRING:
				item.setValue(CJSONString.nextString(chars));
				//System.out.println(item.getValue());
			//	System.out.println(CJSONType.STRING + "  " + item.getName() + "  " + item.getValue());
				break;
				
			case INTEGER:
				item.setValue(CJSONInteger.nextInt(chars));
			//	System.out.println(CJSONType.INTEGER + "  " + item.getName() + "  " + item.getValue());
				break;
				
			case FLOAT:
				item.setValue(CJSONFloat.nextFloat(chars));
			//	System.out.println(CJSONType.FLOAT + "  " + item.getName() + "  " + item.getValue());
				break;
					
			case BOOLEAN:
				item.setValue(CJSONBoolean.nextBoolean(chars));
			//	System.out.println(CJSONType.BOOLEAN + "  " + item.getName() + "  " + item.getValue());
				break;
		}
	
		return item;
	}
	
	public CJSONString getName() {return name;}
	public CJSONObject getValue() {return value;}
	
	public void setValue(CJSONObject value) {this.value = value;}
	public void setName(CJSONString name) {this.name = name;}
	
	public void addItem(String name, String value) {addItem(new CJSONItem(name, value));}
	public void addItem(String name, CJSONObject value) {addItem(new CJSONItem(name, value));}
	public void addItem(CJSONItem item) {getValue().addItem(item);}
	
	public void addArray(String name, String[] value) {addArray(new CJSONArray(name, value));}
	public void addArray(String name, CJSONObject[] value) {addArray(new CJSONArray(name, value));}
	public void addArray(CJSONArray array) {getValue().addArray(array);}
	
	@Override
	public String toString() {
		return "\"" + name + "\": " + value.toString();
	}

}
