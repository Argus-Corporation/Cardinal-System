package net.argus.database;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;

public class LineValue {
	
	private List<ColumnValue> values = new ArrayList<ColumnValue>();
	
	public LineValue(List<ColumnValue> values) {
		this.values = values;
	}
	
	public LineValue(ColumnValue ... values) {
		this(ArrayManager.toList(values));
	}
	
	public ColumnValue getColumnValue(String name) {
		for(ColumnValue value : values)
			if(name.toUpperCase().equals(value.getColumnName().toUpperCase()))
				return value;
		
		return null;
	}
	
	public Object getValue(String name) {
		ColumnValue value = getColumnValue(name);
		if(value == null)
			return null;
		
		return value.getValue();
	}
	
	@Override
	public String toString() {
		String ret = "[";
		
		for(ColumnValue value : values)
			ret += value.getValue() + ", ";
		
		if(values.size() > 0)
			ret = ret.substring(0, ret.length() - 2);
		
		return ret + "]";
	}

}
