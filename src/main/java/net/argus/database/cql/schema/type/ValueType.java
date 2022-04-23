package net.argus.database.cql.schema.type;

import net.argus.database.DataBase;
import net.argus.database.Table;
import net.argus.database.cql.schema.value.SchemaValue;
import net.argus.database.cql.schema.value.Value;
import net.argus.util.StringManager;

public class ValueType extends SchemaType {

	@Override
	public SchemaValue parse(String word, DataBase base, Table table) {
		Object value = null;
		if(word.startsWith("'") && word.endsWith("'"))
			value = word.substring(1, word.length() - 1);
		else if(word.equals("true") || word.equals("false"))
			value = Boolean.valueOf(word);
		else if(StringManager.isInteger(word))
			value = Integer.valueOf(word);
		
		return new Value(value);
	}

}
