package net.argus.lang;

import net.argus.file.FileLang;
import net.argus.file.FileSave;

public class LangSaver {
	
	public static void addLangs(FileSave lang) {
		for(int i = 1; i < lang.getNumberLine() + 1; i++) {
			String langName = lang.read(i);
			
			FileLang langFile = new FileLang(langName, lang.getPath());
			LangParser.parser(langName, langFile.getFile());
		}
	}
	
}
