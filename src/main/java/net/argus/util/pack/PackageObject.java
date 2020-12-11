package net.argus.util.pack;

import net.argus.file.cjson.CJSONItem;
import net.argus.file.cjson.CJSONItemArray;
import net.argus.file.cjson.CJSONObject;

public class PackageObject extends CJSONObject {
	
	public PackageObject(String name) {
		super(name);
	}
	
	public PackageObject addItem(String name, String value) {
		super.addItem(new CJSONItem(name, value));
		return this;
	}
	
	public PackageObject addItemArray(String name, CJSONObject[] array) {
		super.addItemArray(new CJSONItemArray(name, array));
		return this;
	}
	
	public PackageObject addItemArray(String name, String[] array) {
		super.addItemArray(new CJSONItemArray(name, array));
		return this;
	}
	
	public PackageObject addItemArray(String name, byte[] array) {
		super.addItemArray(new CJSONItemArray(name, array));
		return this;
	}

}
