package net.argus.lang;

public class LangParser {
	
	private String[] file;
	
	public LangParser(String langName) {
		//System.out.println(langName);
		file = LangSaver.getLangSaver(langName).getFile();
	}
	
	public String getString(String elementName) {
		for(int i = 0; i < file.length; i++) {
			String key = getKey(getLine(i));
			if(key != null && key.equals(elementName))
				elementName = getValue(getLine(i));
		}
		return elementName;
	}
	
	public String getLine(int line) {
		return file[line];
	}
	
	protected String getKey(String line) {
		try {
			return line.substring(0, line.indexOf('='));
		}catch(StringIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	protected String getValue(String line) {
		return line.substring(line.indexOf('=') + 1);
	}
	

}
