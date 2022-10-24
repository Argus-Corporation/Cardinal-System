package net.argus.database.state;

import net.argus.database.Type;

public class ColumnInfoState extends State {
	
	private String name;
	private Type type;
	
	public ColumnInfoState() {}
	
	public ColumnInfoState(String name) {
		this.name = name;
	}
	
	public ColumnInfoState(Type type) {
		this.type = type;
	}
	
	public ColumnInfoState(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public void setName(String name) {this.name = name;}
	public void setType(Type type) {this.type = type;}
	
	public String getName() {return name;}
	public Type getType() {return type;}

}
