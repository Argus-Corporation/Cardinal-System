package net.argus.database.cql.schema.value;

public class ComparatorValue  extends SchemaValue {
	
	private Comparator comparator;
	
	public ComparatorValue(Comparator comparator) {
		this.comparator = comparator;
	}

	@Override
	public Object[] getValues() {
		return new Object[] {comparator};
	}

}
