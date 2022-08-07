package net.argus.beta.net.pack.ctp;

import net.argus.beta.net.pack.PackageDefault;

public abstract class CtpPackageDefault extends PackageDefault {
	
	public static final String DEFAULT_PATH = "ctp";

	public CtpPackageDefault(String path) {
		super("/" + DEFAULT_PATH + "/" + path);
	}

}
