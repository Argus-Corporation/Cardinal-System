package net.argus.util;

public enum Identifiant {
	
	JAR("jar"), DEV("dev");
	
	private String id;
	
	Identifiant(String id) {
		this.id = "0x" + id;
	}
	
	public String getId() {return id;}

}
