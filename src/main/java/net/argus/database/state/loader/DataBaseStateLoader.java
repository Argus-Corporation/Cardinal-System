package net.argus.database.state.loader;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.DataBase;
import net.argus.database.state.DataBaseState;
import net.argus.file.DataBaseFile;

public class DataBaseStateLoader {
	
	public static DataBase getDataBase(DataBaseFile file) {
		return new DataBase(load(file));
	}
	
	public static DataBaseState load(DataBaseFile file) {
		return load(convert(file.toList()));
	}
	
	public static DataBaseState load(String saved) {
		List<Character> chars = getChars(saved);
		StateKey key = StateKey.next(chars);
		if(key == null)
			return null;

		return (DataBaseState) key.getState(chars);
	}
	
	public static String convert(List<String> datas) {
		String saved = "";
		for(String str : datas)
			saved += str;
		return saved;
	}
	
	public static List<Character> getChars(String line) {
		List<Character> chars = new ArrayList<Character>();
		boolean inString = false;
		for(int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if(ch == '\\') {
				char next = line.charAt(i+1);
				if(next == '\'' || next == '\\') {
					chars.add(next);
					i++;
					continue;
				}
			}
			
			if(!inString && (ch == ' ' || ch == '\t'))
				continue;
			
			if(ch == '\'')
				inString = !inString;
			
			chars.add(ch);
		}
		
		return chars;
	}

}
