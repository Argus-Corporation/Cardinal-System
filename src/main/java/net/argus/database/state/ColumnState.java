package net.argus.database.state;

import java.util.List;

public class ColumnState extends State {
	
	private ColumnInfoState info;
	private List<Object> values;
	
	public ColumnState(ColumnInfoState info, List<Object> values) {
		this.info = info;
		this.values = values;
	}

	public ColumnInfoState getInfo() {return info;}
	public List<Object> getValues() {return values;}

	public void setInfo(ColumnInfoState info) {this.info = info;}
	public void setValues(List<Object> values) {this.values = values;}

}
