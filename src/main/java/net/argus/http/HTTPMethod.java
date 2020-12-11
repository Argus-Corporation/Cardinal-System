package net.argus.http;

public enum HTTPMethod {
	
	CONNECT,
	DELETE,
	GET,
	HEAD,
	OPTIONS,
	PATCH,
	POST,
	PUT,
	TRACE;
	
	public HTTPMethod getHTTPMethod(String value) {
		HTTPMethod[] methods = values();
		for(HTTPMethod met : methods)
			if(value.toUpperCase().equals(met.toString()))
				return met;
			
		return null;
	}

}
