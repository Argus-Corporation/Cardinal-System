package net.argus.database;

public class ColumnValue {
	
	private ColumnInfo columnInfo;
	private Object value;
	
	public ColumnValue(String name, Object value) {
		this.columnInfo = new ColumnInfo(name, (value instanceof Boolean)?Type.BOOLEAN:(value instanceof Integer)?Type.INT:Type.STRING);
		this.value = value;
	}
	
	public ColumnValue(ColumnInfo columnInfo, Object value) {
		this.columnInfo = columnInfo;
		this.value = value;
	}
	
	public ColumnInfo getColumnInfo() {return columnInfo;}
	public String getColumnName() {return columnInfo.getName();}
	public Object getValue() {return value;}
	
	public static ColumnValue getDefault(ColumnInfo info) {
		if(info != null)
			return new ColumnValue(info, info.getType().getDefaultValue());
		
		return null;
	}
	
	@Override
	public String toString() {
		String value = this.value.toString();
		
		if(columnInfo.getType() == Type.STRING)
			value = "'" + this.value + "'";
		
		return columnInfo + "={" + value + "}";
	}

}
