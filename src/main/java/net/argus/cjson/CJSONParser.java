package net.argus.cjson;

import java.util.ArrayList;
import java.util.List;

import net.argus.cjson.value.CJSONObject;
import net.argus.file.CJSONFile;

public class CJSONParser {	
	
	public static CJSON getCJSON(CJSONFile file) {
		return getCJSON(file.toArray());
	}
	
	public static CJSON getCJSON(String[] lines) {
		return getCJSON(getLine(lines));
	}
	
	public static CJSON getCJSON(String cjsonFile) {
		CJSONObject mainObj = CJSONObject.nextObject(getChars(cjsonFile));
		
		return new CJSON(mainObj);
	}
	
	public static String getLine(String[] lines) {
		String l = "";
		
		boolean inString = false;
 line:  for(int i = 0; i < lines.length; i++) {
			String line = lines[i];
			for(int j = 0; j < line.length(); j++) {
				char ch = line.toCharArray()[j];
				
				if(ch == '"')
					if(j > 0 && line.charAt(j - 1) != '\\')
						inString = !inString;
					else if(j <= 0)
						inString = !inString;
					
				
				if(!inString && j + 1 < line.length())
					if(ch == '/' && line.charAt(j + 1) == '/')
						continue line;
				l += ch;
			}
		}
		
		return l;
	}
	
	public static List<Character> getChars(String line) {
		List<Character> chars = new ArrayList<Character>();
		boolean inString = false;
		for(int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if(ch == '\\'){
				char next = line.charAt(i+1);
				if(next == '"' || next == '\\') {
					chars.add(next);
					i++;
					continue;
				}
			}
			
			if(!inString && (ch == ' ' || ch == '\t'))
				continue;
			
			if(ch == '"')
				inString = !inString;
			
			chars.add(ch);
		}
		
		return chars;
	}

}
