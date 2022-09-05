package net.argus.beta.net.pack.def;

import java.net.URL;

import net.argus.beta.net.pack.Package;
import net.argus.beta.net.pack.PackageDefault;
import net.argus.beta.net.pack.PackagePrefab;
import net.argus.system.Network;

public class PingPackageDefault extends PackageDefault {

	public PingPackageDefault() {
		super("/ping");
	}

	@Override
	public Package getPackage(URL url) {
		if(!Network.alreadyChecked())
			Network.checkConnection();
		
		return PackagePrefab.getPingPackage();
	}

}
