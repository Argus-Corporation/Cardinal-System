package net.argus.database;

import net.argus.database.state.ColumnInfoState;
import net.argus.exception.DataBaseIllegalStateException;

public class ColumnInfo {
	
	private String name;
	private Type type;
	
	public ColumnInfo(ColumnInfoState state) {
		if(state.getName() == null  || state.getName().equals("") || state.getType() == null)
			throw new DataBaseIllegalStateException("state value not valid !");
		
		this.name = state.getName();
		this.type = state.getType();
	}
	
	public ColumnInfo(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {return name;}
	public Type getType() {return type;}
	
	public ColumnInfoState getState() {
		return new ColumnInfoState(name, type);
	}
	
	@Override
	public String toString() {
		return "column:" + name + "@" + type;
	}

}
