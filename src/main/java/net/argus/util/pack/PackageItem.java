package net.argus.util.pack;

import net.argus.file.cjson.CJSONItem;
import net.argus.file.cjson.CJSONObject;

@SuppressWarnings("deprecation")
public class PackageItem extends CJSONItem {
	
	public PackageItem(String name) {
		super(name);
	}
	
	public PackageItem(String name, String value) {
		super(name, value);
	}
	
	public PackageItem(String name, CJSONObject value) {
		super(name, value);
	}

}
