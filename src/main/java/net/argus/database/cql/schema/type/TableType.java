package net.argus.database.cql.schema.type;

import net.argus.database.DataBase;
import net.argus.database.Table;
import net.argus.database.cql.schema.value.SchemaValue;
import net.argus.database.cql.schema.value.TableValue;

public class TableType extends SchemaType {

	@Override
	public SchemaValue parse(String word, DataBase base, Table table) {
		int index = base.indexOf(word.substring(1, word.length() - 1));
		
		if(index == -1)
			return new TableValue(null);
		
		return new TableValue(base.getTable(index));
	}

}
