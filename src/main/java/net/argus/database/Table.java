package net.argus.database;

import java.util.List;

import net.argus.database.state.TableState;
import net.argus.exception.DataBaseIllegalStateException;

public class Table {

	private String name;
	private TableMap map;
	
	public Table(TableState state) {
		if(state.getName() == null  || state.getName().equals("") || state.getMapState() == null)
			throw new DataBaseIllegalStateException("state value not valid !");
		
		this.name = state.getName();
		this.map = new TableMap(state.getMapState());
	}
	
	public void insertLine(LineValue value) {
		map.put(value);
	}
	
	public void update(String columnName, Object oldValue, LineValue newValue) {
		map.updateLine(columnName, oldValue, newValue);
	}
	
	public void delete(String columnName, Object value) {
		map.delete(columnName, value);
	}
	
	public List<List<Object>> getAll() {
		return map.getValues();
	}
	
	public ColumnValue get(String columnName, String whereColumn, Object whereValue) {
		LineValue values = getLine(whereColumn, whereValue);
		if(values == null)
			return null;
		
		return values.getColumnValue(columnName);
	}
	
	public List<Object> getColumn(String columnName){
		return map.getColumn(columnName);
	}
	
	public LineValue getLine(String column, Object value) {
		return map.getLine(column, value);
	}
	
	
	public int indexOfValue(String columnName, Object value) {
		return map.indexOfValue(columnName, value);
	}
	
	public String getName() {return name;}
	
	public TableState getState() {
		return new TableState(name, map.getState());
	}
	
	@Override
	public String toString() {
		return "table:" + name + "@" + map;
	}
	
}
