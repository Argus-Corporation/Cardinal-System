package net.argus.file.cjson;

import net.argus.util.CharacterManager;

public class CJSONNumber {

	public static boolean nextIsInteger(char[] chars) {
		boolean inte = true;
		for(int i = 0; i < chars.length; i++) {
			if(chars[i] == '.' || CharacterManager.isNumber(chars[i])) {
				if(chars[i] == '.') inte = false;
			}else break;
		}
		return inte;
	}

}
