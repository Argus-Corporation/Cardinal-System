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
	
	public static Package genPack(String path, Object ... keys) {
		Object[] objs = new Object[keys.length + 2];
		objs[0] = "path";
		objs[1] = path;
		for(int i = 0; i < keys.length; i++)
			objs[i + 2] = keys[i];
		
		return PackageBuilder.getPack(objs);
	}
	
	public static Package getDefaultPackage(String path) {
		return genPack(path);
	}
	
	
	/**CTP**/
	public static Package getCtpRequestPackage(String userName, String password, String authority) {
		return genPack("/ctp/request", "user_name", userName, "password", password, "authority", authority);
	}
	
	public static Package getSessionTokenPackage(SessionToken sessionToken) {
		return genPack("/ctp/session-token", "session_token", sessionToken.getSessionToken());
	}
	
	public static Package getErrorPackage(String originePath, String message) {
		return genPack("/ctp/error", "origine_path", originePath, "message", message);
	}
	
	
	/**CQL**/
	public static Package getCqlQueryPackage(String query, String token) {
		return genPack("/cql/query", "query", query, "session_token", token);
	}
	
	public static Package getCqlQueryResultPackage(String result) {
		return genPack("/cql/query-result", "result", result);
	}
	
	/**PING**/
	public static Package getPingPackage() {
		return genPack("/ping", "start_time", Long.toString(System.currentTimeMillis()));
	}
	
	public static Package getPongPackage(int ping) {
		return genPack("/pong", "ping", ping);
	}
	
}
