package net.argus.database.cql;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.DataBase;
import net.argus.database.cql.schema.Schema;
import net.argus.database.cql.schema.SchematicValue;

public abstract class CQLKeyWord {
	
	private static List<CQLKeyWord> keyWords = new ArrayList<CQLKeyWord>();
	
	public static final SELECT SELECT = new SELECT();
	public static final INSERT INSERT = new INSERT();
	
	private String word;
	
	private Schema schema;
	
	public CQLKeyWord(String word, Schema schema) {
		this.word = word;
		this.schema = schema;
		
		keyWords.add(this);
	}
	
	public static CQLKeyWord getKeyWord(String word) {
		for(CQLKeyWord key : keyWords)
			if(key.word.toUpperCase().equals(word.toUpperCase()))
				return key;
		return null;
	}
	
	public String getKeyWord() {return word;}
	public Schema getSchema() {return schema;}
	
	public abstract CQLRequestReturn execute(SchematicValue values, DataBase base);

}
