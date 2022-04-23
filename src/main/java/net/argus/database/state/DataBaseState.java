package net.argus.database.state;

import java.util.ArrayList;
import java.util.List;

public class DataBaseState {
	
	private String name;
	
	private List<TableState> tableStates = new ArrayList<TableState>();
	
	public DataBaseState() {}
	
	public DataBaseState(String name) {
		this.name = name;
	}
	
	public DataBaseState(List<TableState> tableStates) {
		if(tableStates != null)
			this.tableStates = tableStates;
	}
	
	public DataBaseState(String name, List<TableState> tableStates) {
		if(tableStates != null)
			this.tableStates = tableStates;
		
		this.name = name;
	}
	
	public void setName(String name) {this.name = name;}
	
	public void addTableState(TableState state) {tableStates.add(state);}
	
	public String getName() {return name;}
	public List<TableState> getTableStates() {return tableStates;}

}
