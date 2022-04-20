package net.argus.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
	
	private Map<ColumnInfo, List<Object>> map = new HashMap<ColumnInfo, List<Object>>();
	
	public void addColumn(ColumnInfo info) {
		map.put(info, new ArrayList<Object>());
	}
	
	public List<Object> getColumn(ColumnInfo info) {
		return map.get(info);
	}

}
