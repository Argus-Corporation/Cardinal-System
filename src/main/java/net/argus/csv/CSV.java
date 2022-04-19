package net.argus.csv;

import java.util.ArrayList;
import java.util.List;

import net.argus.file.CSVFile;

public class CSV {
	
	private static CSVSeparator separator = CSVSeparator.COMMA;
	private List<CSVDescripteur> descripteurs = new ArrayList<CSVDescripteur>();
	private List<CSVObject> objects = new ArrayList<CSVObject>();
	
	public CSV(List<CSVDescripteur> descripteurs, List<CSVObject> objects) {
		this.descripteurs = descripteurs;
		this.objects = objects;
	}
	
	public CSVObject getObject(int i) {
		return objects.get(i);
	}
	
	public CSVValue getValues(int objectIndex, int descripteurIndex) {
		return objects.get(objectIndex).get(descripteurIndex);
	}
	
	public List<CSVValue> getValues(int i) {
		List<CSVValue> values = new ArrayList<CSVValue>();
		for(CSVObject obj : objects)
			values.add(obj.get(i));
		
		return values;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		if(descripteurs.size() <= 0)
			return null;
		
		for(CSVDescripteur des : descripteurs)
			str += des.toString() + CSV.separator;
		
		if(str.length() > 0) {
			str = str.substring(0, str.length() - 1);
			str += "\n";
		}
		
		for(CSVObject obj : objects)
			str += obj.toString() + "\n";
		
		if(str.length() > 0)
			str = str.substring(0, str.length() - 1);
		
		return str;
	}
	
	public List<CSVObject> getObjects() {return objects;}
	
	public List<CSVDescripteur> getDescripteurs() {return descripteurs;}
	
	public static CSVSeparator getSeparator() {return separator;}
	
	public static void main(String[] args) {
		CSVFile file = new CSVFile("D:\\Django\\Document 1\\Git\\Eclipse\\test.csv");
		System.out.println(CSVParser.parseCSV(file));
	}

}
