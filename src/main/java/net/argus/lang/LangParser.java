package net.argus.lang;

import java.util.ArrayList;
import java.util.List;

public class LangParser {
	
	public static void parser(String langName, String[] file) {
		List<LangValue> values = new ArrayList<LangValue>();
		for(String line : file) 
			values.add(nextLine(line));
		
		LangManager.addLang(langName, values);
	}
	
	public static LangValue nextLine(String line) {
		LangValue value = new LangValue();
		String key = getKey(line);
		if(key != null) {
			value.setKey(key);
			value.setValue(getValue(line));
		}
		
		return value;
	}
	
	protected static String getKey(String line) {
		try {
			return line.substring(0, line.indexOf('='));
		}catch(StringIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	protected static String getValue(String line) {
		return line.substring(line.indexOf('=') + 1);
	}
	

}
