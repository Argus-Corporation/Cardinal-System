package net.argus.beta.com.pack;

import net.argus.cjson.CJSON;
import net.argus.cjson.CJSONParser;
import net.argus.cjson.value.CJSONObject;

public class Package extends CJSON {
	
	private Package(CJSONObject mainObject) {
		super(mainObject);
	}
	
	public Package(PackageBuilder builder) {
		super(builder);
	}
	
	public static Package parsePackage(String packageText) {
		return new Package(CJSONParser.getCJSON(packageText).getMainObject());
	}
	
}
