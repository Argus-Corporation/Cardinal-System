package net.argus.database.cql.schema.value;

import java.util.List;

import net.argus.database.TableMap;

public class ColumnValue extends SchemaValue {
	
	private String name;
	private List<Object> columnValue;
	
	public ColumnValue(String name, List<net.argus.database.ColumnValue> columnValue) {
		this.name = name;
		this.columnValue = TableMap.convertToObject(columnValue);
		
	}

	@Override
	public Object[] getValues() {
		return new Object[] {name, columnValue};
	}

}
