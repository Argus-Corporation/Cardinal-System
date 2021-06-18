package net.argus.util.pack;

import net.argus.file.cjson.CJSON;
import net.argus.file.cjson.CJSONPareser;

@SuppressWarnings("deprecation")
public class PackagePareser {
	
	public static Package parse(String file) {
		CJSON son = CJSONPareser.parse(file);
		System.out.println(new Package(new PackageBuilder(son)) + "   hedsfg");
		return new Package(new PackageBuilder(son));
	}

}
