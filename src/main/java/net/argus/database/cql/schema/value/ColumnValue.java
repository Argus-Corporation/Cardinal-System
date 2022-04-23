package net.argus.database.cql.schema.value;

import java.util.List;

public class ColumnValue extends SchemaValue {
	
	private String name;
	private List<Object> columnValue;
	
	public ColumnValue(String name, List<Object> columnValue) {
		this.name = name;
		this.columnValue = columnValue;
	}

	@Override
	public Object[] getValues() {
		return new Object[] {name, columnValue};
	}

}
