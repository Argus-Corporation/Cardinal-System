package net.argus.file.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;

public class CJSONItemArray {
	
	private CJSONString name;
	private CJSONObject[] values;
	
	public static CJSONItemArray nextArray(char[] chars) {
		CJSONItemArray itemArray = new CJSONItemArray();
		List<CJSONObject> items = new ArrayList<CJSONObject>();
		
		chars = CJSONPareser.removeNextArray(chars);
		chars = ArrayManager.remove(chars, 0);
		chars = ArrayManager.remove(chars, chars.length - 1);
		
		for(int i = 0; i < chars.length; i++) {
			items.add(CJSONItem.nextItem(chars).getValue());
			int index = ArrayManager.indexOf(chars, ',') + 1;
			chars = ArrayManager.remove(chars, index!=0?index:chars.length, chars.length);
		}
		
		itemArray.setValues((CJSONObject[]) items.toArray(new CJSONObject[items.size()]));
		
		return itemArray;
	}
	
	public CJSONString getName() {return name;}
	public CJSONObject[] getValues() {return values;}

	public void setValues(CJSONObject[] values) {this.values = values;}
	public void setName(CJSONString name) {this.name = name;}
	
	@Override
	public String toString() {
		String txt = "[name " + name + "] [values ";
		
		for(int i= 0; i < values.length - 1; i++)
			txt += values[i] + ", ";
		txt += values[values.length - 1] + "]";
			
		return txt;
	}

}
