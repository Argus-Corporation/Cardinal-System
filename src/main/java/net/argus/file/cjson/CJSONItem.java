package net.argus.file.cjson;

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
		
		switch(CJSONType.nextType(chars)) {
			case OBJECT:
				item.setValue(CJSONObject.nextObject(chars));
				break;

			case STRING:
				item.setValue(CJSONString.nextString(chars));
				break;
				
			case INTEGER:
				item.setValue(CJSONInteger.nextInt(chars));
				break;
			
			case FLOAT:
				item.setValue(CJSONFloat.nextFloat(chars));				
				break;
				
			case BOOLEAN:
				item.setValue(CJSONBoolean.nextBoolean(chars));
				break;
			
		}
		
		return item;
	}
	
	public CJSONString getName() {return name;}
	public CJSONObject getValue() {return value;}
	
	public void setValue(CJSONObject value) {this.value = value;}
	public void setName(CJSONString name) {this.name = name;}
	
	@Override
	public String toString() {
		return "[name: " + name + "] [value: " + value + "]";
	}

}
