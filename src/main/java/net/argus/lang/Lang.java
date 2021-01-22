package net.argus.lang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.argus.file.FileManager;
import net.argus.file.FileSave;
import net.argus.file.Properties;
import net.argus.system.Temp;
import net.argus.util.debug.Debug;

public class Lang {
	
	private static LangType langType;
	private static LangParser parser;
	
	private static FileSave langConfig;
	
	private static void setLang(LangType lang) {
		Lang.parser = new LangParser(lang.getName());
		Lang.langType = lang;
		
		lang.getDefaultLangValue().apply();
		
		Debug.log("Lang: " + langType);
	}
	
	public static void setLang(Properties config) {
		langConfig = new FileSave("lang", "save", new File((config.getBoolean("lang.temp")?Temp.getTempDir():FileManager.getMainPath()) + "/" + config.getString("lang.repertoirfile")), new String[] {"type", "lang", "name"});
		LangSaver.addLangs(langConfig);
		
		setLang(convert(config.getString("lang")));
	}
	
	public static void updateLang(LangType type) {
		setLang(type);
		LangRegistry.update();
	}
	
	public static String getLangName() {return langType.getName();}
	public static LangParser getLang() {return parser;}
	
	public static String getElement(String name) {return parser.getString(name);}
	
	public static String[] getAllLangRelName() {
		List<String> langName = new ArrayList<String>();
		for(int i = 1; i < langConfig.getNumberLine() + 1; i++)
			langName.add(LangType.getLangType(langConfig.read(i)).getRelName());
		
		return (String[]) langName.toArray(new String[langName.size()]);
	}
	
	public static LangType convert(int langId) {return LangType.getLangType(langId);}
	
	public static LangType convert(String langName) {return LangType.getLangType(langName);}
	
	public static String normalized(String langName, Properties config) {
		if(isExist(langName, langConfig))
			return langName;
		else
			return langConfig.read(1);
	}
	
	public static boolean isExist(String langName, FileSave langFile) {
		for(int i = 1; i < langFile.getNumberLine() + 1; i++)
			if(langName != null && langFile.read(i) != null && langName.contains(langFile.read(i)))
				return true;
		return false;
	}
	
}
