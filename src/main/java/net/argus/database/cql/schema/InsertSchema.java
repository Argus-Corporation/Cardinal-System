package net.argus.database.cql.schema;

import static net.argus.database.cql.schema.type.SchemaType.TABLE;

import net.argus.database.cql.schema.type.SchemaType;

public class InsertSchema extends Schema {
	
	public InsertSchema() {
		super(SchemaType.getSchema(TABLE));
	}

}
