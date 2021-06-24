package net.argus.file;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.argus.Cardinal;
import net.argus.instance.Instance;
import net.argus.io.FileIO;
import net.argus.system.OS;

public class FileError extends CardinalFile {
	
	public static final String EXTENTION = "err.log";
	
	public static final Filter FILTER = new Filter(EXTENTION, "Error File");

	public FileError(String fileName) {
		super(fileName, EXTENTION, "/", Instance.SYSTEM);
	}
	
	public void writeError(Throwable e) throws IOException {
		if(!exists()) {
			createFile();
			writeAppend(getInfo());
		}else
			writeAppend("\n\t------------------------------\n");
		
		writeAppend(Long.toString(System.currentTimeMillis()));
		e.printStackTrace(new PrintStream(FileIO.openOutputStream(getFile(), true)));
	}
	
	private static String[] getInfo() {
		List<String> infos = new ArrayList<String>();
		infos.add("OS: " + OS.currentOS());
		infos.add("Java-Version: " + System.getProperty("java.version"));		
		infos.add("Cardinal-Version: " + Cardinal.VERSION);
		infos.add("Programe-Name: " + System.getProperty("name"));
		
		infos.add("\n");
		
		return (String[]) infos.toArray(new String[infos.size()]);
	}
	
}
