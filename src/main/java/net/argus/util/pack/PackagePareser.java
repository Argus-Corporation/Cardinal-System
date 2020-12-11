package net.argus.util.pack;

import net.argus.file.cjson.CJSON;
import net.argus.file.cjson.CJSONPareser;

public class PackagePareser {
	
	public static Package parse(String file) {
		CJSON son = CJSONPareser.parse(file);
		
		return new Package(new PackageBuilder(son));
	}

}
