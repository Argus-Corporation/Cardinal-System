package net.argus.file;

import java.io.File;
import java.io.IOException;

import net.argus.cjson.CJSON;
import net.argus.cjson.CJSONBuilder;
import net.argus.instance.Instance;

public class CJSONFile extends CardinalFile {
	
	public static final String EXTENTION = "cjson";
	
	public static final Filter CJSON_FILTER = new Filter(EXTENTION, "CJSON File");

	public CJSONFile(String fileName, String extention, String rep) {super(fileName, extention, rep);}
	public CJSONFile(String fileName, String extention, String rep, Instance instance) {super(fileName, extention, rep, instance);}
	
	public CJSONFile(String fileName, String rep) {this(fileName, EXTENTION, rep);}
	public CJSONFile(String fileName, String rep, Instance instance) {super(fileName, EXTENTION, rep, instance);}
	
	public CJSONFile(File path) {super(path);}
	public CJSONFile(File path, Instance instance) {super(path, instance);}
	
	public void write(CJSONBuilder builder) throws IOException {
		this.write(new CJSON(builder));
	}
	
	public void write(CJSON cjson) throws IOException {
		super.clear();
		super.write(cjson.toString());
	}

}
