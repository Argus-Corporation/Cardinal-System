package net.argus.file.cjson;

import net.argus.util.CharacterManager;

@Deprecated
public class CJSONFloat extends CJSONElement {

	private float value;
	
	public CJSONFloat(float value) {this.value = value;}
	public CJSONFloat() {this(0);}
	
	public static CJSONFloat nextFloat(char[] chars) {
		String num = "";
		
		for(char car : chars) {
			if(CharacterManager.isNumber(car) || car == '.') {
				num += car;
			}else break;
		}
		
		return new CJSONFloat(Float.valueOf(num));
	}
	
	@Override
	public String toString() {return String.valueOf(value);}
	
}
