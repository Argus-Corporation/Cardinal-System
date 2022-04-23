package net.argus.database.cql.schema.type;

import net.argus.database.DataBase;
import net.argus.database.Table;
import net.argus.database.cql.schema.value.SchemaValue;

public abstract class SchemaType {
	
	public static final SchemaType REGISTRE = new RegistreType();
	public static final SchemaType TABLE = new TableType();
	public static final SchemaType COLUMN = new ColumnType();
	public static final SchemaType VALUE = new ValueType();
	public static final SchemaType COMPARATOR = new ComparatorType();
	
	public static SchemaType[] getSchema(SchemaType ... types) {
		return types;
	}
	
	public abstract SchemaValue parse(String word, DataBase base, Table table);
	
}
