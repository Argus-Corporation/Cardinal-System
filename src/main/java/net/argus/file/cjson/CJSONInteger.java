package net.argus.file.cjson;

import net.argus.util.ArrayManager;

public class CJSONInteger extends CJSONElement {
	
	private int value;
	
	public CJSONInteger(int value) {this.value = value;}
	public CJSONInteger() {this(0);}
	
	public static CJSONInteger nextInt(char[] chars) {
		String num = "";
		
		while(true) {
			if(chars[0] == ',') break;
			if(chars[0] == '}') break;
			
			num += chars[0];
			chars = ArrayManager.remove(chars, 0);
			
			if(!ArrayManager.isExist(chars, 0)) break;
		}
		
		return new CJSONInteger(Integer.valueOf(num));
	}
	
	@Override
	public String toString() {return String.valueOf(value);}

}
