package net.argus.file;

import java.io.File;
import java.io.FileNotFoundException;

import net.argus.lang.Lang;
import net.argus.lang.LangType;
import net.argus.system.Temp;

public class FileLang extends AbstractFileSave {
	
	private static final String extention = "lang";

	public FileLang(Properties config) {
		super(Lang.normalized(config.getString("lang"), config), extention, new File((config.getBoolean("lang.temp")?Temp.getTempDir():FileManager.getMainPath()) + "/" + config.getString("lang.repertoirfile")));
	}
	
	public FileLang(String langName, FileLang old) {
		super(LangType.getLangType(langName).getName(), extention, new File(old.getPath().substring(0, old.getPath().lastIndexOf("/"))));
	}
	
	public String getElementString(String elementName) {
		return getString(elementName);
	}
	
	protected String getString(String elementName) {
		try {
			for(int i = 1; i < getNumberLine() + 1; i++) {
				String key = getKey(getLine(i));
				if(key.equals(elementName))
					elementName = getValue(getLine(i));
			}
		}catch(FileNotFoundException e) {}
		return elementName;
	}
	
	protected String getKey(String line) {
		return line.substring(0, line.indexOf('='));
	}
	
	protected String getValue(String line) {
		return line.substring(line.indexOf('=') + 1);
	}
	
}
