package net.argus.lang;

import java.util.ArrayList;
import java.util.List;

import net.argus.file.css.CSSFile;

public class LangManager {
	
	private static List<LangManager> langValues = new ArrayList<LangManager>();	
	
	private String langName;
	private List<LangValue> langValue;
	private CSSFile cssFile;
	
	public LangManager(String langName, List<LangValue> langValues, CSSFile cssFile) {
		this.langName = langName;
		this.langValue = langValues;
		this.cssFile = cssFile;
	}
	
	public static void addLang(String langName, List<LangValue> langValue, CSSFile cssFile) {
		LangManager.langValues.add(new LangManager(langName, langValue, cssFile));
	}
	
	public static String getElement(String key) {
		int index = getIndex(Lang.getLangName());
		if(index != -1)
			for(LangValue value : langValues.get(index).langValue) {
				String keyElement = value.getKey();
				
				if(keyElement != null && keyElement.equals(key))
					return value.getValue();
			}
		
		return key;
	}
	
	public static CSSFile getCSSFile() {
		int index = getIndex(Lang.getLangName());
		if(index != -1)
			return langValues.get(index).cssFile;
			
		return null;
	}
	
	private static int getIndex(String langName) {
		for(int i = 0; i < langValues.size(); i++)
			if(langValues.get(i).langName.equals(langName))
				return i;
			
		return -1;
	}

}
