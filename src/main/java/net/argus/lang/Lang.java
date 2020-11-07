package net.argus.lang;

import net.argus.file.FileLang;
import net.argus.file.FileSave;
import net.argus.file.Properties;
import net.argus.util.debug.Debug;

public class Lang {
	
	private static String langName;
	private static FileLang lang;
	
	public static void setLangName(LangType lang) {
		langName = lang.getName();
		
		Debug.log("Lang: " + langName);
	}
	
	public static void setLang(Properties config) {Lang.lang = new FileLang(config); setLangName(convert(config.getString("lang")));}
	
	public static String getLangName() {return langName;}
	public static FileLang getLang() {return lang;}
	
	public static LangType convert(int langId) {
		return LangType.getLangType(langId);
	}
	
	public static LangType convert(String langName) {
		return LangType.getLangType(langName);
	}
	
	public static String normalized(String langName, Properties config) {
		FileSave lang = new FileSave("lang", "save", config.getString("lang.repertoirfile"), new String[] {"type", "lang", "name"});
		if(isExist(langName, lang)) {
			return langName;
		}else {
			return lang.read(1);
		}
	}
	
	public static boolean isExist(String langName, FileSave langFile) {
		for(int i = 1; i < langFile.getNumberLine() + 1; i++)
			if(langName != null && langFile.read(i) != null && langName.contains(langFile.read(i)))
				return true;
		return false;
	}
	
}
