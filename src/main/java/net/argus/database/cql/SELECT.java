package net.argus.database.cql;

import net.argus.database.DataBase;
import net.argus.database.LineValue;
import net.argus.database.cql.schema.Schema;
import net.argus.database.cql.schema.SchematicValue;
import net.argus.database.cql.schema.value.Comparator;
import net.argus.database.cql.schema.value.Registre;

public class SELECT extends CQLKeyWord {

	public SELECT() {
		super("SELECT", Schema.SELECT);
	}

	@Override
	public CQLRequestReturn execute(SchematicValue values, DataBase base) {
		Object retVal = null;
		Object[] reg = values.getValue(0);
		
		if(reg[0] == Registre.ALL)
			retVal = values.getTable().getAll();
		else
			retVal = values.getTable().getColumn(reg[1].toString());
		
		if(values.size() == 1)
			return new CQLRequestReturn(true, false, retVal);
		
		String columnName = values.getValue(1)[0].toString();
		Comparator comp = (Comparator) values.getValue(2)[0];
		Object val = values.getValue(3)[0];

		if(comp != Comparator.EQUALS)
			return new CQLRequestReturn(true);
		
		LineValue value = values.getTable().getLine(columnName, val);
		if(value == null)
			return new CQLRequestReturn(true);
		
		return new CQLRequestReturn(true, false, value);
	}

}
