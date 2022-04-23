package net.argus.database.cql.schema;

import static net.argus.database.cql.schema.type.SchemaType.COLUMN;
import static net.argus.database.cql.schema.type.SchemaType.COMPARATOR;
import static net.argus.database.cql.schema.type.SchemaType.REGISTRE;
import static net.argus.database.cql.schema.type.SchemaType.VALUE;

import net.argus.database.cql.schema.type.SchemaType;

public class SelectSchema extends Schema {

	public SelectSchema() {
		super(SchemaType.getSchema(REGISTRE), SchemaType.getSchema(REGISTRE, COLUMN, COMPARATOR, VALUE));
	}
	
}
