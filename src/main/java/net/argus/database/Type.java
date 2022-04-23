package net.argus.database;

public enum Type {
	
	STRING, INT, BOOLEAN;
	
	public Object getDefaultValue() {
		switch(this) {
			case STRING:
				return "";
			case INT:
				return 0;
			case BOOLEAN:
				return false;
		}
		
		return null;
	}
	
	public boolean isValid(Object value) {
		switch(this) {
			case STRING:
				return value instanceof String;
			case INT:
				return value instanceof Integer;
			case BOOLEAN:
				return value instanceof Boolean;
		}
		
		return false;
	}

}
