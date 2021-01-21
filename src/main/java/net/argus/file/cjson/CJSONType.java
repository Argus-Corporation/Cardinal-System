package net.argus.file.cjson;

import net.argus.util.CharacterManager;

public enum CJSONType {
	
	OBJECT, ARRAY, STRING, INTEGER, FLOAT, BOOLEAN;

	public static CJSONType nextType(char[] chars) {
		CJSONType type;
	
		if(CharacterManager.isNumber(chars[0])) {
			type = INTEGER;
			for(char car : chars) {
				if(car == '.' || CharacterManager.isNumber(car)) {
					if(car == '.') type = FLOAT;
				}else break;
			}
		}else if(chars[0] == '"')
			type = STRING;
		else if(chars[0] == '{')
			type = OBJECT;
		else if(chars[0] == '[')
			type = ARRAY;
		else
			type = BOOLEAN;
		
		return type;
	}

}
