package net.argus.csv.value;

import java.util.ArrayList;
import java.util.List;

import net.argus.csv.CSV;
import net.argus.util.ArrayManager;
import net.argus.util.StringManager;

public class CSVObject {
	
	private List<CSVValue> values = new ArrayList<CSVValue>();
	
	public CSVObject(CSVValue ... values) {
		this.values = ArrayManager.convert(values);
	}
	
	public CSVObject(List<CSVValue> values) {
		this.values = values;
	}
	
	public CSVValue get(int i) {
		return values.get(i);
	}
	
	public static CSVObject parse(String line) {
		String[] valuesStr = line.split(CSV.getSeparator().toString());
		List<CSVValue> values = new ArrayList<CSVValue>();
		
		for(int i = 0; i < valuesStr.length; i++) {
			CSVType type = getType(valuesStr[i]);
			if(type == CSVType.NUMBER)
				values.add(new CSVNumber(Float.valueOf(valuesStr[i])));
			else
				values.add(new CSVString(valuesStr[i]));
		}
		
		return new CSVObject(values);
	}
	
	private static CSVType getType(String value) {
		if(StringManager.isFloat(value))
			return CSVType.NUMBER;
		else
			return CSVType.STRING;
	}
	
	@Override
	public String toString() {
		String str = "";
		for(CSVValue val : values)
			str += val.toString() + CSV.getSeparator();
		
		if(str.length() > 0)
			str = str.substring(0, str.length() - 1);
		
		return str;
	}

}
