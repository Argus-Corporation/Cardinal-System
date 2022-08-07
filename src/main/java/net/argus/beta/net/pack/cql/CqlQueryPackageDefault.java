package net.argus.beta.net.pack.cql;

import java.net.URL;

import net.argus.beta.net.pack.Package;
import net.argus.beta.net.pack.PackagePrefab;
import net.argus.beta.net.pack.URLQueryParser;

public class CqlQueryPackageDefault extends CqlPackageDefault {

	public CqlQueryPackageDefault() {
		super("query");
	}

	@Override
	public Package getPackage(URL url) {
		String token = url.getUserInfo();
		if(token == null)
			return null;
		
		URLQueryParser parser = new URLQueryParser(url.getQuery());
		
		return PackagePrefab.getCqlQueryPackage(parser.getValue("query"), token);
	}

}
