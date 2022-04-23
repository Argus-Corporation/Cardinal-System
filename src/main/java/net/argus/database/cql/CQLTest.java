package net.argus.database.cql;

public class CQLTest {
	
	public static void main(String[] args) {
		/*DataBase base = DataBase.genDataBase("DataTest", TableState.getEmptyTableState("empty", new TableSchema(new ColumnInfo("id", Type.INT), new ColumnInfo("username", Type.STRING))));
		base.instert("empty", new LineValue(new ColumnValue("id", 0), new ColumnValue("username", "d")));
		base.instert("empty", new LineValue(new ColumnValue("id", 1), new ColumnValue("username", "h")));
		base.instert("empty", new LineValue(new ColumnValue("id", 2), new ColumnValue("username", "o")));
		
		String request = "SELECT 'empty' * 'username' = 'h'";
		
		CQLRequestReturn ret = CQLParser.analize(request, base);
		System.out.println(ret.getValue());
		
		System.out.println(base);*/
		@SuppressWarnings("unused")
		String savedBase = "data:DataTest@{table:empty@map@[{column:id@INT}={0, 1, 2}, {column:username@STRING}={'d', 'h', 'o'}]}";
		
	}

}
