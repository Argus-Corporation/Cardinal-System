package net.argus.lang;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.argus.file.FileInfo;
import net.argus.file.css.CSSFile;

public class LangParser {
	
	public static void parser(String langName, String[] file) {
		List<LangValue> values = new ArrayList<LangValue>();
		for(String line : file) 
			values.add(nextLine(line));
		
		CSSFile cssFile = null;
		if(isInfoFile(file)) {
			FileInfo infoFile = new FileInfo(getFileInfoName(file[0]), "info", new File(getPathInfo(file[0])));
			String cssPath = infoFile.getValue("CSS-Path");
			
			cssFile = new CSSFile(cssPath.substring(cssPath.lastIndexOf("/")+1), cssPath.substring(0, cssPath.lastIndexOf("/")));

		}
		
		LangManager.addLang(langName, values, cssFile);
	}
	
	public static LangValue nextLine(String line) {
		LangValue value = new LangValue();
		String key = getKey(line);
		if(key != null) {
			value.setKey(key);
			value.setValue(getValue(line));
		}
		
		return value;
	}
	
	public static LangValue nextInfo(String line, String[] infoFile) {
		LangValue value = new LangValue();
		String key = getKey(line);
		//System.out.println(infoFile[0]);
		if(key != null) {
			value.setKey(key);
			value.setValue(infoFile[Integer.valueOf(getValue(line))]);
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
		return line.substring(line.indexOf("#!") + 2, line.lastIndexOf("/"));
	}
	
	protected static String getFileInfoName(String line) {
		return line.substring(line.lastIndexOf("/") + 1);
	}

}
