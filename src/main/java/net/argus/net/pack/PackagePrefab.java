package net.argus.net.pack;

import java.util.ArrayList;
import java.util.List;

import net.argus.net.Profile;
import net.argus.net.socket.CardinalSocket;
import net.argus.util.Version;

public class PackagePrefab {
	
	/**--CONNECTION--**/
	public static Package genConnectionPackage(CardinalSocket socket) {
		PackageBuilder builder = new PackageBuilder(PackageType.CONNECTION);
		
		builder.addKey("Socket", socket.getClass().getName());
		
		Object[] infos = socket.getInfos();
		if(infos != null)
			builder.addKey("Socket-Info", infos);
		
		return builder.genPackage();
	}
	
	/**--VERSION--**/
	public static Package genVersionPackage(Version version) {
		PackageBuilder builder = new PackageBuilder(PackageType.CONNECTION);
		
		builder.addKey("Version", version.getVersion());
		
		return builder.genPackage();
	}
	
	/**--MORE_INFORMATION_CONNECTION--**/
	public static Package genMoreInformationConnectionPackage(CardinalSocket socket, String password) {
		PackageBuilder builder = new PackageBuilder(PackageType.CONNECTION);
		
		builder.addKey("Profile-Name", socket.getProfile().getName());
		builder.addKey("Password", password);
		
		return builder.genPackage();
	}
	
	/**--LOG_OUT--**/
	public static Package genLogOutPackage(String arg) {
		PackageBuilder builder = new PackageBuilder(PackageType.LOG_OUT);
		
		builder.addKey("Argument", arg);
		
		return builder.genPackage();
	}
	
	/**--COMMAND--**/
	public static Package genCommandPackage(String command) {
		PackageBuilder builder = new PackageBuilder(PackageType.COMMAND);
		
		builder.addKey("Command", command);
		
		return builder.genPackage();
	}
	
	/**--PROFILE--**/
	public static Package genProfilePackage(String name) {
		PackageBuilder builder = new PackageBuilder(PackageType.PROFILE);
		
		builder.addKey("Profile-Name", name);
		
		return builder.genPackage();
	}
	
	/**--INFO--**/
	public static Package genInfoPackage(Object[] infos) {
		PackageBuilder builder = new PackageBuilder(PackageType.INFO);
		
		builder.addKey("Infos", infos);

		return builder.genPackage();
	}
	
	public static <E> Package genInfoPackage(List<E> info) {
		return genInfoPackage((Object[]) info.toArray(new Object[info.size()]));
	}
	
	public static Package genInfoPackage(Object info) {
		return genInfoPackage(new Object[] {info});
	}
	
	/**--USER_INFO--**/
	public static Package genUserInfoPackage(Profile profile) {
		List<Object> objs = new ArrayList<Object>();
		
		objs.add("User-Name: " + profile.getName());
		objs.add("Role: " + profile.getRole());
		objs.add("UID: " + profile.getUID());
		
		return genInfoPackage(objs);
	}
	
	/**--SYSTEM--**/
	public static Package genSystemPackage(String info) {
		PackageBuilder builder = new PackageBuilder(PackageType.SYSTEM);
		
		builder.addKey("System-Info", info);

		return builder.genPackage();
	}
	
	/**--PACKAGE--**/
	public static Package genPackage(PackageType type) {
		return new Package(new PackageBuilder(type));
	}

}
