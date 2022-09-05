package net.argus.database.cql;

public class CQLRequestReturn {
	
	private boolean query, error;
	private Object value;
	
	public CQLRequestReturn() {
		this(false, false, null);
	}
	
	public CQLRequestReturn(boolean error) {
		this(false, error, null);
	}
	
	public CQLRequestReturn(Object value) {
		this(true, false, value);
	}
	
	public CQLRequestReturn(boolean query, boolean error, Object value) {
		this.query = query;
		this.error = error;
		this.value = value;
	}
	
	public Object getValue() {return value;}
	
	public boolean isError() {return error;}
	public boolean isQuery() {return query;}
	
	@Override
	public String toString() {
		if(error || value == null)
			return "Error";
		if(!query)
			return "Not query";
		
		return value.toString();
	}
	
}
