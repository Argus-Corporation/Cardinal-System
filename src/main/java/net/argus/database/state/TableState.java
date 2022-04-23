package net.argus.database.state;

import java.util.ArrayList;
import java.util.List;

import net.argus.database.ColumnInfo;
import net.argus.database.TableSchema;

public class TableState {
	
	private String name;
	private TableMapState mapState = new TableMapState();
	
	public TableState() {}
	
	public TableState(String name) {
		this.name = name;
	}
	
	public TableState(TableMapState mapState) {
		if(mapState != null)
			this.mapState = mapState;
	}
	
	public TableState(String name, TableMapState mapState) {
		if(mapState != null)
			this.mapState = mapState;
		
		this.name = name;
	}
	
	public void setName(String name) {this.name = name;}
	public void setMapState(TableMapState mapState) {this.mapState = mapState;}
	
	public String getName() {return name;}
	public TableMapState getMapState() {return mapState;}
	
	public static TableState getEmptyTableState(String name, TableSchema schema) {
		List<List<Object>> values = new ArrayList<List<Object>>();
		List<ColumnInfoState> infoStates = new ArrayList<ColumnInfoState>();
		
		for(ColumnInfo inf : schema.getInfos()) {
			infoStates.add(inf.getState());
			values.add(new ArrayList<Object>());
		}
		
		return new TableState(name, new TableMapState(infoStates, values));
	}

}
