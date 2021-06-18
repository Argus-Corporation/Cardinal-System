package net.argus.util.pack;

import net.argus.file.cjson.CJSONArray;
import net.argus.file.cjson.CJSONItem;
import net.argus.file.cjson.CJSONObject;

@SuppressWarnings("deprecation")
public class PackageObject extends CJSONObject {
	
	public PackageObject addItem(String name, String value) {
		super.addItem(new CJSONItem(name, value));
		return this;
	}
	
	public PackageObject addArray(String name, CJSONObject[] array) {
		super.addArray(new CJSONArray(name, array));
		return this;
	}
	
	public PackageObject addArray(String name, String[] array) {
		super.addArray(new CJSONArray(name, array));
		return this;
	}
	
	public PackageObject addArray(String name, byte[] array) {
		super.addArray(new CJSONArray(name, array));
		return this;
	}

}
