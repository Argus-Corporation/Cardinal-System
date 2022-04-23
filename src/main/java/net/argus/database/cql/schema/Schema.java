package net.argus.database.cql.schema;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.DataBase;
import net.argus.database.Table;
import net.argus.database.cql.schema.type.SchemaType;
import net.argus.database.cql.schema.value.SchemaValue;
import net.argus.util.ArrayManager;

public class Schema {
	
	public static final Schema SELECT = new SelectSchema();
	public static final Schema INSERT = new InsertSchema();
	
	private List<SchemaType[]> schemas = new ArrayList<SchemaType[]>();
	
	public Schema(SchemaType[] ... schemas) {
		this.schemas = ArrayManager.toListArray(schemas);
	}
	
	public SchematicValue getSchematicValue(String[] words, DataBase base) {
		if(words.length < 1)
			return null;
		
		Table table = getTable(words, base);
		if(table == null)
			return null;
		
		words = ArrayManager.remove(words, 0);
		
		for(int i = 0; i < schemas.size(); i++) {
			if(words.length != size(i))
				continue;
			SchematicValue val = new SchematicValue(table);
			
			for(int j = 0; j < words.length; j++)
				val.addValue(schemas.get(i)[j].parse(words[j], base, table));

			return val;
		}
		return null;
	}
	
	private Table getTable(String[] words, DataBase base) {
		SchemaValue tabVal = SchemaType.TABLE.parse(words[0], base, null);
		if(tabVal == null)
			return null;
		
		Object objTab = tabVal.getValues()[0];
		if(objTab instanceof Table)
			return (Table) objTab;
		return null;
	}
	
	public int size(int schemaIndex) {return schemas.get(schemaIndex).length;}

}
