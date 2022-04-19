package net.argus.csv;

import net.argus.file.CSVFile;

public class CSV {
	
	public CSV() {
		
	}
	
	public static void main(String[] args) {
		CSVFile file = new CSVFile("C:\\Users\\Django\\Documents\\Perso\\Cardinal\\Eclipse\\test.csv");
		CSVParser.parseCSV(file);
	}

}
