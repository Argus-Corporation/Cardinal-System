package net.argus.database.cql.schema.value;

public class RegistreValue extends SchemaValue {
	
	private Registre reg;
	private String columnName;
	
	public RegistreValue(Registre reg) {
		this(reg, null);
	}
	
	public RegistreValue(Registre reg, String columnName) {
		this.reg = reg;
		this.columnName = columnName;
	}

	@Override
	public Object[] getValues() {
		return new Object[] {reg, columnName};
	}

}
