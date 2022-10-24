package net.argus.database.cql;

import net.argus.database.DataBase;
import net.argus.database.cql.schema.SchematicValue;
import net.argus.util.ArrayManager;

public class CQLParser {
	
	public static CQLRequestReturn analize(String request, DataBase base) {
		String[] words = request.split(" ");
		if(words.length < 1)
			return new CQLRequestReturn(true);

		CQLKeyWord primary = CQLKeyWord.getKeyWord(words[0]);
		if(primary == null)
			return new CQLRequestReturn(true);

		SchematicValue values = primary.getSchema().getSchematicValue(ArrayManager.remove(words, 0), base);
		if(values == null || values.getTable() == null)
			return new CQLRequestReturn(true);
		
		return primary.execute(values, base);
	}

}
