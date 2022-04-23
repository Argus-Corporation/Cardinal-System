package net.argus.database.cql.schema.type;

import net.argus.database.DataBase;
import net.argus.database.Table;
import net.argus.database.cql.schema.value.ColumnValue;
import net.argus.database.cql.schema.value.SchemaValue;

public class ColumnType extends SchemaType {

	@Override
	public SchemaValue parse(String word, DataBase base, Table table) {
		String name = word.substring(1, word.length() - 1);
		return new ColumnValue(name, table.getColumn(name));
	}

}
