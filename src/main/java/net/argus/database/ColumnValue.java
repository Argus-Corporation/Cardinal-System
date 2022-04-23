package net.argus.database;

public class ColumnValue {
	
	private String columnName;
	private Object value;
	
	public ColumnValue(String columnName, Object value) {
		this.columnName = columnName;
		this.value = value;
	}
	
	public String getColumnName() {return columnName;}
	public Object getValue() {return value;}
	
	public static ColumnValue getDefault(ColumnInfo inf) {
		if(inf != null)
			return getDefault(inf.getName(), inf.getType());
		
		return null;
	}
	
	public static ColumnValue getDefault(String name, Type type) {
		if(name != null && !name.equals("") && type != null)
			return new ColumnValue(name, type.getDefaultValue());
		
		return null;
	}

}
