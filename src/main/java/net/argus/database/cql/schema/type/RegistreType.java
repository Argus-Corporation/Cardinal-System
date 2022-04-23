package net.argus.database.cql.schema.type;

import net.argus.database.DataBase;
import net.argus.database.Table;
import net.argus.database.cql.schema.value.Registre;
import net.argus.database.cql.schema.value.RegistreValue;
import net.argus.database.cql.schema.value.SchemaValue;

public class RegistreType extends SchemaType {

	@Override
	public SchemaValue parse(String word, DataBase base, Table table) {
		Registre reg = null;
		String columnName = null;
		
		if(word.equals("*"))
			reg = Registre.ALL;
		else if(word.startsWith("'") && word.endsWith("'") && word.length() > 2) {
			reg = Registre.COLUMN;
			columnName = word.substring(1, word.length() - 1);
		}
			
		return new RegistreValue(reg, columnName);
			
	}

}
