package net.argus.database.cql.schema.value;

import net.argus.database.Table;

public class TableValue extends SchemaValue {
	
	private Table table;
	
	public TableValue(Table table) {
		this.table = table;
	}

	@Override
	public Object[] getValues() {
		return new Object[] {table};
	}
	
}
