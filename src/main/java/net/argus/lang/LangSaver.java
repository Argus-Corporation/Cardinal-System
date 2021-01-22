package net.argus.lang;

import java.util.ArrayList;
import java.util.List;

import net.argus.file.FileLang;
import net.argus.file.FileSave;

public class LangSaver {
	
	private String langName;
	private String[] file;
	
	public LangSaver(String langName, String[] file) {
		this.langName = langName;
		this.file = file;
	}
	
	public String getLangName() {return langName;}
	public String[] getFile() {return file;}
	
	private static List<LangSaver> langSavers = new ArrayList<LangSaver>();
	
	public static void addLangs(FileSave lang) {
		for(int i = 1; i < lang.getNumberLine()+1; i++) {
			String langName = lang.read(i);
			
			FileLang langFile = new FileLang(langName, lang.getPath());
			LangSaver saver = new LangSaver(langName, langFile.getFile());
			
			langSavers.add(saver);
		}
	}
	
	public static LangSaver getLangSaver(String langName) {
		for(LangSaver lang : langSavers)
			if(langName.equals(lang.getLangName()))
				return lang;
		
		return null;
	}
	
}
