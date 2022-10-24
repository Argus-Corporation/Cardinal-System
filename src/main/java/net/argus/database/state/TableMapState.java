package net.argus.database.state;

import java.util.ArrayList;
import java.util.List;

public class TableMapState extends State {
	
	private List<ColumnInfoState> infos = new ArrayList<ColumnInfoState>();
	private List<List<Object>> values = new ArrayList<List<Object>>();
	
	public TableMapState() {}
	
	public TableMapState(List<ColumnInfoState> infoState, List<List<Object>> values) {
		this.infos = infoState;
		this.values = values;
	}
	
	public void setInfoState(List<ColumnInfoState> infoState) {this.infos = infoState;}
	public void setValues(List<List<Object>> values) {this.values = values;}
	
	public List<ColumnInfoState> getInfoStates() {return infos;}
	public List<List<Object>> getValues() {return values;}

}
