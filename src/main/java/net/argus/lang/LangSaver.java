package net.argus.lang;

import java.io.File;

import net.argus.file.FileLang;
import net.argus.file.FileSave;

public class LangSaver {
	
	public static void addLangs(FileSave lang) {
		for(int i = 0; i < lang.countLine(); i++) {
			String langName = lang.getValue(i+1);
			
			FileLang langFile = new FileLang(langName, new File(lang.getFile().getParent()));
			
			LangType type = LangType.getLangType(langName);
			LangParser.parser(type, langFile.toArray());
			Lang.addLang(type, langFile);
		}
	}
	
}
