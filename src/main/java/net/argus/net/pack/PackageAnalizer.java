package net.argus.net.pack;

import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.key.PackageKey;
import net.argus.util.ArrayManager;

public class PackageAnalizer {
	
	public static Package analyze(String[] lines) {
		return analyze(ArrayManager.toList(lines));
	}
	
	public static Package analyze(List<String> lines) {
		return new Package(convertToKey(lines));
	}
	
	public static List<PackageKey> convertToKey(List<String> lines){
		List<PackageKey> keys = new ArrayList<PackageKey>();
		try {
			keys = PackageKey.getKeys(lines);
		}catch(StringIndexOutOfBoundsException e) {return PackagePrefab.genLogOutPackage("error").getKeys();};
		return keys;
	}

}
