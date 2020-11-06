package net.argus.file;

import java.io.FileNotFoundException;

import net.argus.Lang;

public class FileLang extends AbstractFileSave {
	
	private static final String extention = "lang";

	public FileLang(Properties config) {
		super(Lang.normalized(config.getString("lang"), config), extention, config.getString("lang.repertoirfile"));
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
