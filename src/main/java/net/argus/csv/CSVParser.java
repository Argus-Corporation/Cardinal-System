package net.argus.csv;

import java.util.ArrayList;
import java.util.List;

import net.argus.file.CSVFile;

public class CSVParser {
	
	public static CSV parseCSV(CSVFile file) {
		String[] lines = file.toArray();
		
		return parseCSV(lines);
	}
	
	public static CSV parseCSV(String[] file) {
		List<CSVDescripteur> descripteurs = CSVDescripteur.parse(file[0]);
		List<CSVObject> objects = new ArrayList<CSVObject>();
		for(int i = 1; i < file.length; i++)
			objects.add(CSVObject.parse(file[i]));
		
		return new CSV(descripteurs, objects);
	}
}
