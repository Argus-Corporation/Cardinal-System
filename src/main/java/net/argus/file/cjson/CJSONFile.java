package net.argus.file.cjson;

import java.io.File;
import java.io.IOException;

import net.argus.file.AbstractFileSave;
import net.argus.file.Filter;

public class CJSONFile extends AbstractFileSave {
	
	public static final String EXTENTION = "cjson";
	
	public static final Filter CJSON_FILTER = new Filter(EXTENTION, "CJSON File");

	public CJSONFile(String fileName, String extention, String rep) {super(fileName, extention, rep);}
	public CJSONFile(String fileName, String rep) {this(fileName, EXTENTION, rep);}
	
	public CJSONFile(String fileName, File path) {super(fileName, EXTENTION, path);}
	public CJSONFile(File path) {super(path);}
	
	public void write(CJSONBuilder build) throws IOException {
		super.clear();
		super.write(build.getFile());
	}

}
