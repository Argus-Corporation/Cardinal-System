package net.argus.beta.net.pack;

import net.argus.cjson.Array;
import net.argus.cjson.CJSON;
import net.argus.cjson.CJSONParser;
import net.argus.cjson.value.CJSONObject;
import net.argus.cjson.value.CJSONValue;

public class PackageReturn {
	
	private CJSON respons;
		
	public PackageReturn(String respons) {
		this.respons = CJSONParser.getCJSON(respons);
	}
	
	public String getString(String path) {
		return respons.getString(path);
	}
	
	public int getInteger(String path) {
		return respons.getInt(path);
	}
	
	public boolean getBoolean(String path) {
		return respons.getBoolean(path);
	}
	
	public CJSONObject getObject(String path) {
		return respons.getObject(path);
	}
	
	public Array getArray(String path) {
		return respons.getArray(path);
	}
	
	public CJSONValue getValue(String path) {
		return respons.getValue(path);
	}
	
	@Override
	public String toString() {
		return respons.toString();
	}

}

