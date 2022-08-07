package net.argus.beta.net.pack;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.argus.beta.net.session.SessionToken;

public class PackagePrefab {
	
	private static List<PackageDefaultHandler> handlers = new ArrayList<PackageDefaultHandler>();
	
	public static Package getDefaultPackageWithPath(URL url) {
		for(PackageDefaultHandler h : handlers) {
			Package pack = h.getDefault(url);
			if(pack != null)
				return pack;
		}
		return null;
	}
	
	public static void addPackageDefaultHandler(PackageDefaultHandler h) {handlers.add(h);}
	
	
	/**CTP**/
	public static Package getCtpRequestPackage(String userName, String password, String authority) {
		return PackageBuilder.getPack("path", "/ctp/request", "user_name", userName, "password", password, "authority", authority);
	}
	
	public static Package getSessionTokenPackage(SessionToken sessionToken) {
		return PackageBuilder.getPack("path", "/ctp/session-token", "session_token", sessionToken.getSessionToken());
	}
	
	
	/**CQL**/
	public static Package getCqlQueryPackage(String query, String token) {
		return PackageBuilder.getPack("path", "/cql/query", "query", query, "session_token", token);
	}
	
}
