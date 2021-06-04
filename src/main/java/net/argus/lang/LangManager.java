package net.argus.lang;

import java.util.ArrayList;
import java.util.List;

import net.argus.file.css.CSSFile;

public class LangManager {
	
	private static List<LangManager> langValues = new ArrayList<LangManager>();	
	
	private LangType type;
	private LangValues values;
	private CSSFile cssFile;
	
	public LangManager(LangType type, LangValues values, CSSFile cssFile) {
		this.type = type;
		this.values = values;
		this.cssFile = cssFile;
	}
	
	public static void addLang(LangType type, LangValues values, CSSFile cssFile) {
		LangManager.langValues.add(new LangManager(type, values, cssFile));
	}
	
	public static String getElement(String key) {
		int index = getIndex();
		if(index != -1)
			if(index < langValues.size())
				return langValues.get(index).values.getValue(key);
		
		return key;
	}
	
	public static CSSFile getCSSFile() {
		int index = getIndex();
		if(index != -1 && index < langValues.size())
			return langValues.get(index).cssFile;
			
		return null;
	}
	
	private static int getIndex() {
		for(int i = 0; i < langValues.size(); i++)
			if(langValues.get(i).type.equals(Lang.currentLang()))
				return i;
			
		return -1;
	}

}
