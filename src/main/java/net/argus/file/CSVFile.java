package net.argus.file;

import java.io.File;
import java.io.IOException;

import net.argus.csv.CSV;
import net.argus.csv.CSVBuilder;
import net.argus.instance.Instance;

public class CSVFile extends CardinalFile {
	
	public static final String EXTENTION = "csv";
	
	public static final Filter CJSON_FILTER = new Filter(EXTENTION, "CSV File");

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
	
	public void write(CSVBuilder builder) throws IOException {
		this.write(builder.build());
	}
	
	public void write(CSV csv) throws IOException {
		super.clear();
		super.write(csv.toString());
	}

}
