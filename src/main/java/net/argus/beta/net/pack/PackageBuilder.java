package net.argus.beta.net.pack;

import net.argus.cjson.CJSONItem;
import net.argus.cjson.value.CJSONArray;
import net.argus.cjson.value.CJSONBoolean;
import net.argus.cjson.value.CJSONInteger;
import net.argus.cjson.value.CJSONNull;
import net.argus.cjson.value.CJSONString;
import net.argus.cjson.value.CJSONValue;

public class PackageBuilder {
	
	public static Package getPack(Object ... entrys) {
		if(entrys.length % 2 != 0)
			return null;
		
		PackageEntry[] packs = new PackageEntry[entrys.length / 2];
		
		for(int i = 0; i < entrys.length; i += 2)
			packs[i==0?0:i/2] = new PackageEntry(entrys[i].toString(), entrys[i + 1]);
		
		return getPackArray(null, packs);
	}
	
	public static Package getPack(PackageType type, Object ... entrys) {
		if(entrys.length % 2 != 0)
			return null;
		
		PackageEntry[] packs = new PackageEntry[entrys.length / 2];
		
		for(int i = 0; i < entrys.length; i += 2)
			packs[i==0?0:i/2] = new PackageEntry(entrys[i].toString(), entrys[i + 1]);
		
		return getPackArray(type, packs);
	}
	
	public static Package getPack(PackageType type, PackageEntry ... entrys) {
		return getPackArray(type, entrys);
	}
	
	public static Package getPackArray(PackageType type, PackageEntry[] entrys) {
		Package pack = new Package();
		if(type != null)
			pack.addItem(new CJSONItem("type", new CJSONInteger(type.getType())));
		
		for(PackageEntry entry : entrys) {
			CJSONValue val = null;

			if(entry.getValue() == null)
				val = new CJSONNull();
			else if(entry.getValue() instanceof String)
				val = new CJSONString((String) entry.getValue());
			else if(entry.getValue() instanceof Integer)
				val = new CJSONInteger((int) entry.getValue());
			else if(entry.getValue() instanceof Boolean)
				val = new CJSONBoolean((boolean) entry.getValue());
			else if(entry.getValue() instanceof CJSONValue[])
				val = new CJSONArray((CJSONValue[]) entry.getValue());
			
			pack.addItem(new CJSONItem(entry.getName(), val));
		}
		
		return pack;
	}

}
