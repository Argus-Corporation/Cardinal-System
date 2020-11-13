package net.argus.file.cjson;

import net.argus.util.CharacterManager;

public class CJSONInteger extends CJSONObject {
	
	private int value;
	
	public CJSONInteger(int value) {this.value = value;}
	public CJSONInteger() {this(0);}
	
	public static CJSONInteger nextInt(char[] chars) {
		String num = "";
		
		for(char car : chars) {
			if(CharacterManager.isNumber(car)) {
				num += car;
			}else break;
		}
		
		return new CJSONInteger(Integer.valueOf(num));
	}
	
	@Override
	public String toString() {return String.valueOf(value);}

}
