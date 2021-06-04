package net.argus.cjson.value;

import java.util.ArrayList;
import java.util.List;

import net.argus.cjson.CJSONItem;

public class CJSONObject extends CJSONValue {
	
	private List<CJSONItem> items = new ArrayList<CJSONItem>();
	
	public CJSONObject() {}
	public CJSONObject(List<CJSONItem> items) {this.items = items;}
	
	public static CJSONObject nextObject(List<Character> chars) {
		List<CJSONItem> items = new ArrayList<CJSONItem>();
		
		chars.remove(0); //remove "{"
		if(chars.get(0) != '}')
			items.add(CJSONItem.nextItem(chars));

		while(chars.get(0) == ',') {
			chars.remove(0);  //remove ","
			items.add(CJSONItem.nextItem(chars));
			if(chars.get(0) == '}')
				break;
		}
		chars.remove(0);  //remove "}"
		
		return new CJSONObject(items);
	}
	
	public void addItem(CJSONItem item) {items.add(item);}
	public void addItem(String name, CJSONValue value) {items.add(new CJSONItem(new CJSONString(name), value));}
	
	@Override
	public List<CJSONItem> getValue() {return items;}
	
	@Override
	public String toString() {
		String str = "{";
		for(int i = 0; i < items.size() - 1; i++)
			str += items.get(i).toString() + ", ";
		
		if(items.size() > 0)
			str += items.get(items.size() - 1);
		
		str += "}";
		
		return str;
	}

}
