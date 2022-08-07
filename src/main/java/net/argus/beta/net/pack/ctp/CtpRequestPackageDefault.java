package net.argus.beta.net.pack.ctp;

import java.net.URL;

import net.argus.beta.net.pack.Package;
import net.argus.beta.net.pack.PackagePrefab;
import net.argus.beta.net.pack.URLQueryParser;

public class CtpRequestPackageDefault extends CtpPackageDefault {

	public CtpRequestPackageDefault() {
		super("request");
	}

	@Override
	public Package getPackage(URL url) {
		String userInfo = url.getUserInfo();
		if(!userInfo.contains(":") || url.getQuery() == null)
			return null;
		
		String userName = userInfo.substring(0, userInfo.indexOf(':'));
		String password = userInfo.substring(userInfo.indexOf(':') + 1, userInfo.length());
		
		URLQueryParser parser = new URLQueryParser(url.getQuery());
		
		return PackagePrefab.getCtpRequestPackage(userName, password, parser.getValue("authority"));
	}

}
