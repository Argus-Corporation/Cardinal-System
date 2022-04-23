package net.argus.database.cql;

import net.argus.database.DataBase;
import net.argus.database.cql.schema.Schema;
import net.argus.database.cql.schema.SchematicValue;

public class INSERT extends CQLKeyWord {

	public INSERT() {
		super("INSERT", Schema.INSERT);
	}

	@Override
	public CQLRequestReturn execute(SchematicValue values, DataBase base) {
		return null;
	}

}
