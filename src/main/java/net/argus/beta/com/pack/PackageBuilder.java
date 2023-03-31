package net.argus.beta.com.pack;

import net.argus.cjson.CJSONBuilder;

public class PackageBuilder extends CJSONBuilder {
	
	public PackageBuilder() {
		super();
	}
	
	public PackageBuilder(Package pack) {
		super(pack.getMainObject());
	}

}
