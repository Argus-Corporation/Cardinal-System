package net.argus.beta.net.pack.cql;

import net.argus.beta.net.pack.PackageDefault;

public abstract class CqlPackageDefault extends PackageDefault {
	
	public static final String DEFAULT_PATH = "cql";

	public CqlPackageDefault(String path) {
		super("/" + DEFAULT_PATH + "/" + path);
	}

}
