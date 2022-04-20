package net.argus.csv.value;

public class CSVValue {
	
	protected Object value;
	
	public CSVValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}

}
