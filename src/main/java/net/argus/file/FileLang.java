package net.argus.file;

import java.io.File;

import net.argus.lang.LangType;

public class FileLang extends CardinalFile {
	
	public static final String extention = "lang";
	public static final Filter langFilter = new Filter("lang", "Lang file");
	
	public FileLang(String langName, String rep) {
		super(langName, extention, rep);
	}
	
	public FileLang(String langName, File path) {
		super(new File(path + "/" + LangType.getLangType(langName).getName() + "." + extention));
	}
	
	public FileLang(String langName, FileLang old) {this(langName, new File(old.getFile().getParent()));}
	
}
