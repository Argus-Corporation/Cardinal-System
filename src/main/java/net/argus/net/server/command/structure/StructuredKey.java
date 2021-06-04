package net.argus.net.server.command.structure;

public class StructuredKey {

	private String value;
	private KeyType type;
	
	public StructuredKey(String value, KeyType type) {
		this.value = value;
		this.type = type;
	}
	
	public Object getValue() {
		switch(type) {
			case BOOLEAN:
				return Boolean.valueOf(value);
			case INT:
				return Integer.valueOf(value);
			case STRING:
				return value;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
