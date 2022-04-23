package net.argus.database.cql.schema;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.Table;
import net.argus.database.cql.schema.value.SchemaValue;

public class SchematicValue {
	
	private List<SchemaValue> values = new ArrayList<SchemaValue>();
	private Table table;
	
	public SchematicValue(Table table) {
		this.table = table;
	}
	
	public void addValue(SchemaValue value) {values.add(value);}
	
	public Object[] getValue(int index) {return values.get(index).getValues();}
	public Table getTable() {return table;}
	
	public int size() {return values.size();}

}
