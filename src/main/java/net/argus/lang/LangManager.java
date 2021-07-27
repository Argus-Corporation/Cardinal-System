package net.argus.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import net.argus.file.css.CSSFile;

public class LangManager {
	
	private static List<LangManager> langValues = new ArrayList<LangManager>();	
	
	private LangType type;
	private LangValues values;
	private LangCSS css = new LangCSS();
	
	public LangManager(LangType type, LangValues values, CSSFile cssFile) {
		this.type = type;
		this.values = values;
		this.css.addCSS(cssFile);
	}
	
	public static void addLang(LangType type, LangValues values, CSSFile cssFile) {
		if(!isRegister(type))
			LangManager.langValues.add(new LangManager(type, values, cssFile));
		else {
			int index = getIndex(type);
			if(index != -1) {
				LangManager mana = LangManager.langValues.get(index);
				
				mana.css.addCSS(cssFile);
				
				if(mana.values != null) {
					Set<Entry<String, String>> enu = values.getValues().entrySet();
					for(Entry<String, String> val : enu)
						mana.values.putValue(val.getKey(), val.getValue());
				}else
					mana.values = values;			
			}
		}
	}
	
	public static String getElement(String key) {
		int index = getIndex();
		if(index != -1)
			if(index < langValues.size())
				return langValues.get(index).values.getValue(key);
		
		return key;
	}
	
	public static void putElement(String key, String value, LangType type) {
		int index = getIndex(type);

		if(index != -1) {
			if(index < langValues.size())
				langValues.get(index).values.putValue(key, value);
		}else
			addLang(type, new LangValues().putValue(key, value), null);
				
	}
	
	public static LangCSS getCSS() {
		int index = getIndex();
		if(index != -1 && index < langValues.size())
			return langValues.get(index).css;
			
		return null;
	}
	
	private static int getIndex() {
		return getIndex(Lang.currentLang());
	}
	
	private static int getIndex(LangType type) {
		for(int i = 0; i < langValues.size(); i++)
			if(langValues.get(i).type.equals(type))
				return i;
			
		return -1;
	}
	
	private static boolean isRegister(LangType type) {
		for(LangManager lm : langValues)
			if(lm.type.equals(type))
				return true;
		return false;
	}

}
