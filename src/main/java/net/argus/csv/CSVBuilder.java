package net.argus.csv;

import java.util.ArrayList;
import java.util.List;

import net.argus.csv.value.CSVDescripteur;
import net.argus.csv.value.CSVObject;
import net.argus.csv.value.CSVValue;
import net.argus.util.ArrayManager;

public class CSVBuilder {
	
	private List<CSVDescripteur> descripteurs = new ArrayList<CSVDescripteur>();
	private List<CSVObject> objects = new ArrayList<CSVObject>();
	
	public CSVBuilder() {}
	
	public void addDescripteur(String descripteurName) {
		descripteurs.add(new CSVDescripteur(descripteurName, descripteurs.size()));
	}
	
	public void addDescripteur(CSVDescripteur descripteur) {
		descripteurs.add(descripteur);
	}
	
	public void addDescripteurs(List<CSVDescripteur> descripteurs) {
		ArrayManager.add(this.descripteurs, descripteurs);
	}
	
	public void addObject(Object ... values) {
		CSVValue[] csvValues = new CSVValue[values.length];
		for(int i = 0; i < values.length; i++)
			csvValues[i] = new CSVValue(values[i]);
			
		addObject(csvValues);
	}
	
	public void addObject(CSVValue ... values) {
		addObject(new CSVObject(values));
	}
	
	public void addObject(CSVObject object) {
		objects.add(object);
	}
	
	public void addObjects(List<CSVObject> objects) {
		ArrayManager.add(this.objects, objects);
	}
	
	public CSV build() {
		return new CSV(descripteurs, objects);
	}
	
	public List<CSVDescripteur> getDescripteurs() {
		return descripteurs;
	}
	
	public List<CSVObject> getObjects() {
		return objects;
	}

}
