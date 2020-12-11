package net.argus.http;

import java.io.File;

import net.argus.file.FileManager;

public class HTTPFile {
	
	private String path, fileName, extention;
	
	public HTTPFile() {
		setPath(null);
		setFileName(null);
		setExtention(null);
	}
	
	public File getFile() {
		return new File(FileManager.getMainPath() + "/www/" + path + fileName + "." + extention);
	}
	
	public void setPath(String path) {this.path = path!=null?path:"/";}
	public void setFileName(String fileName) {this.fileName = fileName!=null?fileName:"index";}
	public void setExtention(String extention) {this.extention = extention!=null?extention:"html";}

}
