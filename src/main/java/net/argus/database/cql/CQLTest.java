package net.argus.database.cql;

import java.io.IOException;

import net.argus.database.DataBase;
import net.argus.database.state.loader.DataBaseStateLoader;
import net.argus.file.DataBaseFile;

public class CQLTest {
	
	public static void main(String[] args) throws IOException {
		/*DataBase base = DataBase.genDataBase("DataTest", TableState.getEmptyTableState("empty", new TableSchema(new ColumnInfo("id", Type.INT), new ColumnInfo("username", Type.STRING))));
		base.addTable(TableState.getEmptyTableState("profile", new ColumnInfo("first-name", Type.STRING), new ColumnInfo("last-name", Type.STRING)));
		base.instert("empty", new LineValue(new ColumnValue("id", 0), new ColumnValue("username", "d")));
		base.instert("empty", new LineValue(new ColumnValue("id", 1), new ColumnValue("username", "h")));
		base.instert("empty", new LineValue(new ColumnValue("id", 2), new ColumnValue("username", "o")));
		
		String request = "SELECT 'empty' *";
		
		CQLRequestReturn ret = CQLParser.analize(request, base);
		System.out.println(ret.getValue());
		
		System.out.println(base);*/
		String savedBase = "data:DataTest@{table:empty@map@[{column:id@INT}={0, 1, 2}, {column:username@STRING}={'django', 'harry potter', 'oceane'}]}, {table:profile@map@[{column:first-name@STRING}={}, {column:last-name@STRING}={}]}";
		/*System.out.println(
				//CQLParser.getCQL(savedBase)
				new DataBase(DataBaseStateLoader.load(savedBase))
				//DataBaseStateLoader.load(savedBase).getTableStates().get(0).getMapState().getInfoStates().get(0).getType()
		)
		;*/
		
		/*DataBase base = new DataBase(DataBaseStateLoader.load(savedBase));
		System.out.println(base);
		DataBaseFile file = new DataBaseFile("C:\\Users\\malas\\Documents\\base.cql");
		file.write(base);
		//System.out.println(CQLParser.analize("SELECT 'empty' 'id' 'username' = 'django'", base).getValue());
		*/
		
		DataBase base = DataBaseStateLoader.getDataBase(new DataBaseFile("C:\\Users\\malas\\Documents\\base.cql"));
		System.out.println(CQLParser.analize("SELECT 'Test' * ", base));
	}

}
