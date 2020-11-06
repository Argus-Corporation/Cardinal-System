package net.argus.util;

public enum PackageType {
	
	MESSAGE(0), SYSTEM(1), PSEUDO(2), ARRAY(3), COMMANDE(-1), LOG_OUT(-2), PASSWORD(-3);
	
	private int id;
	
	private PackageType(int id) {
		this.id = id;
	}
	
	public int getId() {return id;}
	
	public static PackageType getPackageType(int id) {
		for(PackageType pt : PackageType.values())
			if(pt.getId() == id)
				return pt;
		
		return null;
	}

}
