package net.argus.util;

import java.util.ArrayList;
import java.util.List;

public class StringManager {
	
	public static String[] valueOf(String[] strs) {
		boolean inStr = false;
		List<String> list = new ArrayList<String>();
		String inValue = "";
		
		for(String str : strs) {
			char[] chars = str.toCharArray();
			String value = "";
			
			for(int i = 0; i < chars.length; i++) {
				if(chars[i] == '\\')
					i += 2;
				
				if(!inStr && chars[i] == '\"') {
					inStr = true;
					i++;
				}
				
				if(inStr && chars[i] == '\"') {
					inStr = false;
					break;
				}
				
				if(inStr)
					inValue += chars[i];
				else
					value += chars[i];
				
			}
			if(inStr)
				inValue += " ";
			else if(!inValue.equals("")) {
				list.add(inValue);
				inValue = "";
			}else if(!value.equals("")) {
				list.add(value);
				value = "";
			}
		}
		
		return (String[]) list.toArray(new String[list.size()]);
	}
	
	public static String secureString(String str) {
		char[] chars = str.toCharArray();
		String value = "";
		
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == '\"') {
				value += "\\";
			}
			value += chars[i];
		}
		return value;
	}
	
	public static void main(String[] args) {
		String enter = "/kick \"lol h\"";
		for(String str : valueOf(enter.split(" ")))
			System.out.println(str);
	}

}
