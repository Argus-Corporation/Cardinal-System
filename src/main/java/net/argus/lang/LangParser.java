package net.argus.lang;

import java.io.File;

import net.argus.file.FileInfo;
import net.argus.file.css.CSSFile;
import net.argus.instance.Instance;
import net.argus.util.DoubleStock;

public class LangParser {
	
	public static void parser(LangType type, String[] file) {
		LangValues values = new LangValues();
		for(String line : file) 
			values.putValue(nextLine(line));
		
		CSSFile cssFile = null;
		if(isInfoFile(file)) {
			FileInfo infoFile = new FileInfo(new File(getPathInfo(file[0]) + "/" + getFileInfoName(file[0]) + ".info"));
			String cssPath = infoFile.getValue("CSS-Path");
			
			cssFile = new CSSFile(cssPath.substring(cssPath.lastIndexOf("/")+1), cssPath.substring(0, cssPath.lastIndexOf("/")));

		}
		
		LangManager.addLang(type, values, cssFile);
	}
	
	public static DoubleStock<String, String> nextLine(String line) {
		DoubleStock<String, String> value = new DoubleStock<String, String>();
		String key = getKey(line);
		if(key != null) {
			value.setFirst(key);
			value.setSecond(getValue(line));
		}
		
		return value;
	}
	
	protected static String getKey(String line) {
		try {
			return line.substring(0, line.indexOf('='));
		}catch(StringIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	protected static String getValue(String line) {
		return line.substring(line.indexOf('=') + 1);
	}
	
	protected static boolean isInfoFile(String[] file) {
		return file[0].startsWith("#!");
	}
	
	protected static String getPathInfo(String line) {
		String path = line.substring(line.indexOf("#!") + 2, line.lastIndexOf("/"));
		if(path.startsWith("."))
			path = Instance.currentInstance().getRootPath() + "/" + path.substring(1);
		
		return path;
	}
	
	protected static String getFileInfoName(String line) {
		return line.substring(line.lastIndexOf("/") + 1);
	}

}
