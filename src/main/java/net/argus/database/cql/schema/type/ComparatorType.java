package net.argus.database.cql.schema.type;

import net.argus.database.DataBase;
import net.argus.database.Table;
import net.argus.database.cql.schema.value.Comparator;
import net.argus.database.cql.schema.value.ComparatorValue;
import net.argus.database.cql.schema.value.SchemaValue;

public class ComparatorType extends SchemaType {

	@Override
	public SchemaValue parse(String word, DataBase base, Table table) {
		Comparator comp = null;
		switch(word) {
			case "=":
				comp = Comparator.EQUALS;
				break;
			case ">=":
				comp = Comparator.SUPERIOR_EQUALS;
				break;
			case "<=":
				comp = Comparator.INFERIOR_EQUALS;
				break;
			case ">":
				comp = Comparator.SUPERIOR;
				break;
			case "<":
				comp = Comparator.INFERIOR;
				break;
		}
		
		return new ComparatorValue(comp);
	}

}
