package net.argus.beta.net.pack;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class PackageDefault implements PackageDefaultHandler {
	
	private static List<PackageDefault> defaults = new ArrayList<PackageDefault>();
	
	private String path;
	
	public PackageDefault(String path) {
		if(!path.startsWith("/"))
			path = "/" + path;
		
		this.path = path;
		defaults.add(this);
	}

	@Override
	public Package getDefault(URL url) {
		for(PackageDefault def : defaults)
			if(def.path.equals(url.getPath()))
				return def.getPackage(url);
		return null;
	}
	
	public abstract Package getPackage(URL url);

}
