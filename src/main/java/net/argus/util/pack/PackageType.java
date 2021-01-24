package net.argus.util.pack;

public enum PackageType {
	
	MESSAGE(0), SYSTEM(1), PSEUDO(2), ARRAY(3), FILE(4), NOTIFY(5), COMMANDE(-1), LOG_OUT(-2), PASSWORD(-3);
	
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
