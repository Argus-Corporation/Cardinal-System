package net.argus.csv;

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
