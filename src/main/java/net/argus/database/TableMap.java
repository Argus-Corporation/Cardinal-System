package net.argus.database;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.state.ColumnInfoState;
import net.argus.database.state.TableMapState;
import net.argus.exception.DataBaseException;
import net.argus.exception.DataBaseIllegalStateException;

public class TableMap {
	
	private List<ColumnInfo> infos = new ArrayList<ColumnInfo>();
	private List<List<Object>> values = new ArrayList<List<Object>>();
	
	public TableMap(TableMapState state) {
		if(state.getInfoStates() == null  || state.getValues() == null)
			throw new DataBaseIllegalStateException("state value not valid !");
		
		this.values = state.getValues();
		
		for(ColumnInfoState ciState : state.getInfoStates())
			infos.add(new ColumnInfo(ciState));
	}
	
	public void put(LineValue value) {
		for(int i = 0; i < infos.size(); i++) {
			Object obj = value.getValue(infos.get(i).getName());
			
			if(obj == null) {
				this.values.get(i).add(infos.get(i).getType().getDefaultValue());
				continue;
			}
				
			if(!infos.get(i).getType().isValid(obj))
				throw new DataBaseException("\"" + obj + "\" is not valid on " + infos.get(i).getType() + " column !");
			
			this.values.get(i).add(obj);
		}
	}
	
	public void updateLine(String columnName, Object oldValue, LineValue newValue) {
		int yIndex = indexOfValue(columnName, oldValue);
		if(yIndex == -1)
			return;
		
		for(int i = 0; i < infos.size(); i++) {
			Object obj = newValue.getValue(infos.get(i).getName());
			
			if(obj == null)
				continue;
				
			if(!infos.get(i).getType().isValid(obj))
				throw new DataBaseException("\"" + obj + "\" is not valid on " + infos.get(i).getType() + " column !");
			
			this.values.get(i).set(yIndex, obj);
		}
	}
	
	public void delete(String columnName, Object value) {
		int yIndex = indexOfValue(columnName, value);
		if(yIndex == -1)
			return;
		
		for(int i = 0; i < infos.size(); i++)
			this.values.get(i).remove(yIndex);
		
	}
		
	public LineValue getLine(String columnName, Object value) {
		int yIndex = indexOfValue(columnName, value);
		if(yIndex == -1)
			return null;
		
		List<ColumnValue> colVals = new ArrayList<ColumnValue>();
		for(int i = 0; i < infos.size(); i++)
			colVals.add(new ColumnValue(infos.get(i).getName(), values.get(i).get(yIndex)));
		
		return new LineValue(colVals);
		
	}
	
	public List<Object> getColumn(ColumnInfo info) {
		return getColumn(info.getName());
	}
	
	public List<Object> getColumn(String name) {
		int index = indexOf(name);
		if(index == -1 || values.size() < index)
			return null;
		
		return values.get(index);
	}
	
	public int indexOf(ColumnInfo info) {
		return indexOf(info.getName());
	}
	
	public int indexOf(String name) {
		for(int i = 0; i < infos.size(); i++)
			if(infos.get(i).getName().toUpperCase().equals(name.toUpperCase()))
				return i;
		return -1;
	}
	
	public int indexOfValue(String name, Object value) {
		List<Object> objs = getColumn(name);
		if(objs != null)
			return objs.indexOf(value);
		
		return -1;
	}
	
	public List<ColumnInfo> getInfos() {return infos;}
	public List<List<Object>> getValues() {return values;}
	
	public TableMapState getState() {
		List<ColumnInfoState> infoState = new ArrayList<ColumnInfoState>();
		
		for(ColumnInfo inf : infos)
			infoState.add(inf.getState());
		
		return new TableMapState(infoState, values);
	}
	
	@Override
	public String toString() {
		String ret = "map@[{";
		for(int i = 0; i < infos.size(); i++) {
			ret += infos.get(i) + "}={";
			for(Object obj : values.get(i)) {
				if(obj instanceof String)
					ret += "'" + obj + "', ";
				else
					ret += obj + ", ";
			}
			
			if(values.get(i).size() > 0)
				ret = ret.substring(0, ret.length() - 2);
			
			ret += "}, {";
			
		}
		
		if(infos.size() > 0)
			ret = ret.substring(0, ret.length() - 3);
		
		return ret + "]";
	}

}
