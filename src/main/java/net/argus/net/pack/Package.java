package net.argus.net.pack;

import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.key.ArrayKey;
import net.argus.net.pack.key.EndKey;
import net.argus.net.pack.key.PackageKey;
import net.argus.util.ArrayManager;

public class Package implements Cloneable {
	
	private List<PackageKey> keys = new ArrayList<PackageKey>();
	
	public Package(List<PackageKey> keys) {
		valueOf(keys);
		this.keys = keys;
	}
	
	public Package(PackageBuilder builder) {
		List<PackageKey> keys = builder.getKeys();
		valueOf(keys);
		this.keys = keys;
	}
	
	private static void valueOf(List<PackageKey> keys) {
		for(int i = keys.size() - 1; i > 0; i--)
			if(keys.get(i).getValue() instanceof EndKey)
				keys.remove(i);
	}
	
	public Object getObject(String name) {
		for(PackageKey k : keys)
			if(k.getName().equals(name))
				if(!(k instanceof ArrayKey))
				return k.getValue();
		return null;
	}
	
	public String getValue(String name) {
		Object obj = getObject(name);
		if(obj == null)
			return null;
		return obj.toString();
	}
	
	public Object[] getArray(String name) {
		for(PackageKey k : keys)
			if(k.getName().equals(name))
				if(k instanceof ArrayKey)
					return k.getValues();
		return null;
	}
	
	public PackageType getType() {
		return PackageType.getPackageTypeByType(getValue("Type").toString());
	}
	
	public List<PackageKey> getKeys() {return keys;}
	
	public boolean isNull() {return getKeys().size()<1;}
	
	public Package clone() throws CloneNotSupportedException {
		Package clone = (Package) super.clone();
		PackageKey[] key = new PackageKey[keys.size()];
		System.arraycopy(keys.toArray(), 0, key, 0, keys.size());
		
		clone.keys = ArrayManager.toList(key);
		return clone;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(PackageKey k : keys) 
			s += k.toString() + "\r\n";
		
		return s;
	}

}
