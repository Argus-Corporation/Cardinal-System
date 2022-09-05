package net.argus.http;

import java.io.File;

import net.argus.file.FileManager;

public class HTTPFile {
	
	private String path;
	
	public HTTPFile() {
		setPath(null);
	}
	
	public File getFile() {
		return new File(FileManager.getMainPath() + "/www" + path);
	}
	
	public void setPath(String path) {this.path = path;}
	
}
