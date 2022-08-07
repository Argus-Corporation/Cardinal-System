package net.argus.beta.net.pack;

import java.util.ArrayList;
import java.util.List;

public class PackageType {
	
	private static List<PackageType> types = new ArrayList<PackageType>();
	
	public static final PackageType COMMAND = new PackageType("command", 1);
	public static final PackageType UNDEFINE = new PackageType("undefined");
	
	public static final PackageType CONNECTION = new PackageType("connection", -1);
	public static final PackageType PROFILE = new PackageType("profile", -2);
	public static final PackageType LOG_OUT = new PackageType("logout", -3);
	public static final PackageType SYSTEM = new PackageType("system", -4);
	public static final PackageType INFO = new PackageType("info", -5);
	
	private String name;
	private int type;
	
	public PackageType(String name, int type) {
		this.name = name;
		while(isUsed(type))
			type++;
		
		this.type = type;
		types.add(this);
	}
	
	PackageType(String name) {
		this.name = name;
	}
	
	public String getName() {return name;}
	public int getType() {return type;}
	
	public static boolean isUsed(int type) {
		for(PackageType pt : types)
			if(pt.type == type)
				return true;
		return false;
	}
	
	public static PackageType getPackageTypeByName(String name) {
		for(PackageType pt : types)
			if(pt.name.equals(name))
				return pt;
		return UNDEFINE;
	}
	
	public static PackageType getPackageTypeByType(int type) {
		for(PackageType pt : types)
			if(pt.type == type)
				return pt;
		return UNDEFINE;
	}
	
	public static PackageType getPackageTypeByType(String strType) {
		int type = Integer.valueOf(strType);
		
		for(PackageType pt : types)
			if(pt.type == type)
				return pt;
		return UNDEFINE;
	}
	
	@Override
	public String toString() {
		return Integer.toString(type);
	}

}
