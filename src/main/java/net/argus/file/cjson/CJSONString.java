package net.argus.file.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;

public class CJSONString extends CJSONObject {
	
	private String value;
	
	public CJSONString(CJSONString cjs) {this(cjs.value);}
	
	public CJSONString(String value) {this.value = value!=null?value:"";}
	
	public static CJSONString[] parse(char[] chars) {
		List<CJSONString> cStrings = new ArrayList<CJSONString>();
		//int count = Counter.countOccurrences(new String(chars), '"') / 2;
		boolean openStr = false;
		int len = chars.length;
		
		String str = "";
		for(int i = 0; i < len; i++) {
			if(chars[0] == '"' && openStr) {
				openStr = false;
				cStrings.add(new CJSONString(str));
				chars = ArrayManager.remove(chars, 0);
				str = "";
			}
						
			if(chars[0] == '"' && !openStr) {
				chars = ArrayManager.remove(chars, 0);
				openStr = true;
			}
						
			if(openStr) {
				str += String.valueOf(chars[0]);
				chars = ArrayManager.remove(chars, 0);
			}else
				chars = ArrayManager.remove(chars, 0);
			
			if(!ArrayManager.isExist(chars, 0)) break;
			
		}
		
		return (CJSONString[]) cStrings.toArray(new CJSONString[cStrings.size()]);
	}
	
	public static CJSONString nextString(char[] chars) {
		boolean openStr = false;
		int len = chars.length;
		
		String str = "";
		for(int i = 0; i < len; i++) {
			if(chars[0] == '\\' && chars[1] == '"') {
				i += 2;
				
				str += "\"";
				chars = ArrayManager.remove(chars, 0);
				chars = ArrayManager.remove(chars, 0);
			}
				
			if(chars[0] == '\\') {
				i += 2;
				
				chars = ArrayManager.remove(chars, 0);
				chars = ArrayManager.remove(chars, 0);
			}
				
			if(chars[0] == '"' && openStr) {
				openStr = false;
				return new CJSONString(str);
			}
						
			if(chars[0] == '"' && !openStr) {
				chars = ArrayManager.remove(chars, 0);
				openStr = true;
			}
				
			if(openStr) {
				str += chars[0];
				chars = ArrayManager.remove(chars, 0);
			}else
				chars = ArrayManager.remove(chars, 0);
			
			if(!ArrayManager.isExist(chars, 0)) break;
			
		}
		
		return null;
	}
	
	@Override
	public String toString() {return value;}
	
	public int length() {return value.length();}
	
	public boolean equals(CJSONString obj) {
		return obj.value.equals(value);
	}

}
