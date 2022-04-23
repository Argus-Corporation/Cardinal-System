package net.argus.csv.value;

public enum CSVSeparator {
	
	COMMA, SEMCOLON;
	
	@Override
	public String toString() {
		if(this == COMMA)
			return ",";
		else
			return ";";
	}

}
