package net.argus.file;

import java.io.File;

import net.argus.lang.LangType;

public class FileLang extends AbstractFileSave {
	
	public static final String extention = "lang";
	public static final Filter langFilter = new Filter("lang", "");
	
	public FileLang(String langName, String path) {
		super(LangType.getLangType(langName).getName(), extention, new File(path.substring(0, path.lastIndexOf("/"))));
	}
	
	public FileLang(String langName, FileLang old) {this(langName, old.getPath());}
	
}
