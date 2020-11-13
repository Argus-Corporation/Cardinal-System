package net.argus.file.cjson;

import net.argus.util.CharacterManager;

public class CJSONItem {
	
	private CJSONString name;
	private CJSONObject value;
	
	public CJSONItem(CJSONString name, CJSONString value) {
		this.name = name;
		this.value = value;
	}
	
	public CJSONItem() {this(null, null);}
	
	public static CJSONItem nextItem(char[] chars) {
		CJSONItem item = new CJSONItem();
		
		if(chars[0] == '"')
			item.setValue(CJSONString.nextString(chars));
		else if(CharacterManager.isNumber(chars[0])) {
			if(CJSONNumber.nextIsInteger(chars)) {
				item.setValue(CJSONInteger.nextInt(chars));
			}else
				item.setValue(CJSONFloat.nextFloat(chars));
		}else if(chars[0] == '{')
			item.setValue(CJSONObject.nextObject(chars));
		
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
