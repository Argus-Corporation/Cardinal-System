package net.argus.file;

import java.io.File;

import net.argus.instance.Instance;

public class CSVFile extends CardinalFile {
	
	public static final String EXTENTION = "csv";
	
	public static final Filter CSV = new Filter(EXTENTION, "CSV File");

	public CSVFile(File file) {
		super(file);
	}

	public CSVFile(String path) {
		super(path);
	}

	public CSVFile(String fileName, String extention, String rep, Instance instance) {
		super(fileName, extention, rep, instance);
	}

	public CSVFile(String fileName, String extention, String rep) {
		super(fileName, extention, rep);
	}

}
