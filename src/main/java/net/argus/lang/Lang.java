package net.argus.lang;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.argus.file.FileLang;
import net.argus.file.FileSave;
import net.argus.file.Properties;
import net.argus.file.css.CSSFile;
import net.argus.gui.FontRegister;
import net.argus.instance.Instance;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class Lang {
	
	private static LangType currentLang;
	
	private static List<Properties> saver = new ArrayList<Properties>();
	
	private static List<LangType> langs = new ArrayList<LangType>();
	
	private static void setLang(LangType lang) {
		if(lang == null)
			return;
		
		if(lang.equals(currentLang))
			return;
		
		Lang.currentLang = lang;
		lang.getDefaultLangValue().apply();
		
		save();
		
		updateCSS();
		
		Debug.log("Lang: " + currentLang.getName());
	}
	
	public static void setLang(Properties config) {
		setLang(config, Instance.currentInstance());
	}
	
	public static void setLang(Properties config, Instance instance) {
		String[] type = new String[] {"type", "lang", "name"};
		
		String rep = config.getString("lang.rep");

		if(rep != null && rep.startsWith("."))
			setLang(new FileSave(new File(instance.getRootPath() + "/" + rep.substring(1) + "/lang.save"), type));
		else
			setLang(new FileSave(new File(rep + "/lang.save"), type));
		
		addSaver(config);
		setLang(LangType.getLangType(config.getString("lang")));
	}
	
	public static void setLang(FileSave lang) {
		LangSaver.addLangs(lang);
	}
	
	public static void updateLang(LangType type) {
		setLang(type);
		LangRegister.update();
	}
	
	private static void updateCSS() {
		CSSFile cssFile = LangManager.getCSSFile();
		if(cssFile != null)
			cssFile.execut();
		
		FontRegister.update();
	}
	
	public static void save() {
		for(Properties config : saver)
			try {config.setKey("lang", currentLang.getName());}
			catch(IOException e) {Debug.log("Saved error", Info.ERROR);}
	}
	
	public static void addLang(LangType type, FileLang lang) {
		LangParser.parser(type, lang.toArray());
		
		if(!langs.contains(type))
			langs.add(type);
	}
	
	public static void addSaver(Properties saver) {
		Lang.saver.add(saver);
	}
	
	public static String[] getAllRealName() {
		List<String> langName = new ArrayList<String>();
		for(LangType type : langs)
			langName.add(type.getRealName());
		
		return (String[]) langName.toArray(new String[langName.size()]);
	}
	
	public static boolean isRegistered(String langName) {
		if(langName == null)
			return false;
		
		for(LangType type : langs)
			if(langName.contains(type.getName()))
				return true;
		return false;
	}
	
	public static boolean isRegistered(LangType type) {
		if(type == null)
			return false;
		
		for(LangType lang : langs)
			if(lang.equals(type))
				return true;
		return false;
	}
	
	public static String get(String key) {return LangManager.getElement(key);}
	
	public static String getLangName() {return currentLang.getName();}
	public static LangType currentLang() {return currentLang;}
	
}
