package net.argus.file.cjson;

public class CJSONBoolean extends CJSONElement {
	
	private boolean value;
	
	public CJSONBoolean(boolean value) {this.value = value;}
	
	public CJSONBoolean() {this(false);}
	
	public static CJSONBoolean nextBoolean(char[] chars) {
		String bool = "";
		
		for(char car : chars) {
			if(car == 't' || car == 'r' || car == 'u' || car == 'e'
					|| car == 'f' || car == 'a' || car == 'l' || car == 's') {
				bool += car;
			}else break;
		}
		return new CJSONBoolean(Boolean.valueOf(bool));
	}
	
	@Override
	public String toString() {return String.valueOf(value);}

}
