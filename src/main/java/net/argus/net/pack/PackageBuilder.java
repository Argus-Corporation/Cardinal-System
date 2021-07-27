package net.argus.net.pack;

import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.key.ArrayKey;
import net.argus.net.pack.key.PackageKey;

public class PackageBuilder {
	
	private List<PackageKey> keys = new ArrayList<PackageKey>();
	
	public PackageBuilder(PackageType type) {
		addKey("Type", type);
	}
	
	public PackageBuilder addKey(String name, Object value) {return addKey(new PackageKey(name, value));}
	public PackageBuilder addKey(String name, Object[] values) {return addKey(new ArrayKey(name, values));}
	
	public PackageBuilder addKey(PackageKey key) {
		if(key != null &&
				key.getName() != null && key.getValue() != null &&
				!key.getName().equals("") && !key.isValueNull())
			keys.add(key);
		else
			return this;
		
		return this;
	}
	
	public Package genPackage() {return new Package(keys);}
	
	public List<PackageKey> getKeys() {return keys;}

}
