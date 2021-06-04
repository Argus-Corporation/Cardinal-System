package net.argus.file;

import java.io.File;

import net.argus.instance.Instance;

public class FileInfo extends CardinalFile {
	
	private String[] file;
	
	/**
	 * 
	 * @param fileName
	 * @param extention
	 * @param rep
	 */
	public FileInfo(String fileName, String extention, String rep) {
		super(fileName, extention, rep);
		copyFile();
	}
	
	/**
	 * 
	 * @param fileName
	 * @param extention
	 * @param rep
	 * @param instance
	 */
	public FileInfo(String fileName, String extention, String rep, Instance instance) {
		super(fileName, extention, rep, instance);
		copyFile();
	}

	/**
	 * 
	 * @param file
	 * @param instance
	 */
	public FileInfo(File file, Instance instance) {
		super(file, instance);
		copyFile();
	}
	
	/**
	 * 
	 * @param file
	 */
	public FileInfo(File file) {
		super(file);
		copyFile();
	}
	
	public String getValue(String key) {
		return getValue(getLineByKey(key));
	}
	
	public int getLineByKey(String key) {
		for(int i = 0; i < file.length; i++) {
			String lineKey = getKey(i);
			if(lineKey.equals(key))
				return i;
		}
		return -1;
	}
	
	public String getKey(int line) {
		return file[line].substring(0, file[line].indexOf(':'));
	}
	
	public String getValue(int line) {
		String str = file[line].substring(file[line].indexOf(':')+1);
		
		while(str.charAt(0) == ' ')
			str = str.substring(1);
		
		return str;
	}

	public void copyFile() {
		file = toArray();
		
		for(int i = 0; i < file.length; i++)
			file[i] = valueOf(file[i]);
	}
	
}
