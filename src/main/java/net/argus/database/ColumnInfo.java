package net.argus.database;

public class ColumnInfo {
	
	private Type type;
	private String name;
	
	public ColumnInfo(Type type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public String getName() {return name;}
	public Type getType() {return type;}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ColumnInfo) {
			ColumnInfo info = (ColumnInfo) obj;
			
			return info.getType()==type&&info.name.equals(name);
		}else
			return false;
	}
	
}
