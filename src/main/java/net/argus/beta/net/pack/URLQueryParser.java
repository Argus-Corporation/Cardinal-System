package net.argus.beta.net.pack;

import java.util.HashMap;
import java.util.Map;

public class URLQueryParser {
	
	private Map<String , String> map = new HashMap<String, String>();
	
	
	public URLQueryParser(String query) {
		if(query == null)
			return;
		
		parse(query);
	}
	
	private void parse(String query) {
		String[] split = query.split("&");
		
		for(String str : split) {
			String[] vals = str.split("=");
			map.put(vals[0], vals[1]);
		}
	}
	
	public String getValue(String name) {
		return map.get(name);
	}

}
