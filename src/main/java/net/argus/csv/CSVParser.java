package net.argus.csv;

import net.argus.file.CSVFile;

public class CSVParser {
	
	public static CSV parseCSV(CSVFile file) {
		String[] lines = file.toArray();
		
		return parseCSV(lines);
	}
	
	public static CSV parseCSV(String[] file) {
		
		return null;
	}
}
