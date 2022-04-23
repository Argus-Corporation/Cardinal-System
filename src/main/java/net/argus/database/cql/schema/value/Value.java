package net.argus.database.cql.schema.value;

public class Value extends SchemaValue {
	
	private Object value;
	
	public Value(Object value) {
		this.value = value;
	}

	@Override
	public Object[] getValues() {
		return new Object[] {value};
	}

}
