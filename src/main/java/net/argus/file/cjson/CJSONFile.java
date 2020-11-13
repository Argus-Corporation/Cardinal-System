package net.argus.file.cjson;

import net.argus.file.AbstractFileSave;

public class CJSONFile extends AbstractFileSave {
	
	public static final String EXTENTION = "cjson";

	public CJSONFile(String fileName, String extention, String rep) {super(fileName, extention, rep);}
	public CJSONFile(String fileName, String rep) {this(fileName, EXTENTION, rep);}

}
