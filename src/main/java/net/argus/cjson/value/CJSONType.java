package net.argus.cjson.value;

public enum CJSONType {
	
	OBJECT, ARRAY, BOOLEAN, INTEGER, STRING, NULL;
	
	public static CJSONType valueOf(CJSONValue value) {
		if(value instanceof CJSONObject)
			return OBJECT;
		if(value instanceof CJSONArray)
			return ARRAY;
		if(value instanceof CJSONBoolean)
			return BOOLEAN;
		if(value instanceof CJSONInteger)
			return INTEGER;
		if(value instanceof CJSONString)
			return STRING;
		if(value instanceof CJSONNull)
			return NULL;
		
		return null;
	}

}
