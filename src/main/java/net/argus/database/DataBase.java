package net.argus.database;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.state.DataBaseState;
import net.argus.database.state.TableState;
import net.argus.exception.DataBaseException;
import net.argus.exception.DataBaseIllegalStateException;
import net.argus.util.ArrayManager;

public class DataBase {
	
	private String name;
	
	private List<Table> tables = new ArrayList<Table>();
	
	public DataBase(DataBaseState state) {
		if(state.getName() == null  || state.getName().equals("") || state.getTableStates() == null)
			throw new DataBaseIllegalStateException("state value not valid !");
		
		this.name = state.getName();
		
		for(TableState tState : state.getTableStates())
			tables.add(new Table(tState));
	}
	
	public void instert(String tableName, LineValue value) {
		Table tab = getTable(tableName);
		
		if(tab == null)
			throw new DataBaseException("table \"" + tableName + "\" is not registered inthis database");
		
		tab.insertLine(value);
	}
	
	public void update(String tableName, String columnName, Object oldValue, LineValue newValue) {
		Table tab = getTable(tableName);
		
		if(tab == null)
			throw new DataBaseException("table \"" + tableName + "\" is not registered inthis database");
		
		tab.update(columnName, oldValue, newValue);
	}
	
	public void delete(String tableName, String columnName, Object value) {
		Table tab = getTable(tableName);
		
		if(tab == null)
			throw new DataBaseException("table \"" + tableName + "\" is not registered inthis database");
		
		tab.delete(columnName, value);
	}
	
	public Object get(String tableName, String columnName, String whereColumn, Object whereValue) {
		Table tab = getTable(tableName);
		
		if(tab == null)
			throw new DataBaseException("table \"" + tableName + "\" is not registered inthis database");
		
		ColumnValue val = tab.get(columnName, whereColumn, whereValue);
		return val!=null?val.getValue():null;
	}
	
	public Table getTable(String tableName) {
		return getTable(indexOf(tableName));
	}
	
	public Table getTable(int index) {
		if(tables.size() > index)
			return tables.get(index);
		return null;
	}
	
	public void addTable(TableState state) {
		if(state == null)
			return;
		tables.add(new Table(state));
	}
	
	public int indexOf(String tableName) {
		for(int i = 0; i < tables.size(); i++)
			if(tables.get(i).getName().toUpperCase().equals(tableName.toUpperCase()))
				return i;
		return -1;
	}
	
	public DataBaseState getState() {
		List<TableState> tableState = new ArrayList<TableState>();
		
		for(Table tab : tables)
			tableState.add(tab.getState());
		
		return new DataBaseState(name, tableState);
	}
	
	public static DataBase genDataBase(String name, TableState ... states) {
		return new DataBase(new DataBaseState(name, ArrayManager.toList(states)));
	}
	
	@Override
	public String toString() {
		String ret = "data:" + name + "@{";
		for(Table table : tables)
			ret += table + "}, {";
		
		if(tables.size() > 0)
			ret = ret.substring(0, ret.length() - 3);
		else
			ret += "}";
		
		return ret;
	}

}
