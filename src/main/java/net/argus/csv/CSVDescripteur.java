package net.argus.csv;

import java.util.ArrayList;
import java.util.List;

public class CSVDescripteur {
	
	private String name;
	private int index;
	
	public CSVDescripteur(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	public static List<CSVDescripteur> parse(String descripteurLine) {
		List<CSVDescripteur> descs = new ArrayList<CSVDescripteur>();
		
		String[] desc = descripteurLine.split(CSV.getSeparator().toString());
		int i = 0;
		for(String des : desc)
			descs.add(new CSVDescripteur(des, i++));
		
		return descs;
	}
	
	public int getIndex() {return index;}
	public String getName() {return name;}
	
	@Override
	public String toString() {
		return name;
	}

}
