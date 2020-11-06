package net.argus.util;

public class Package {
	
	private PackageType type;
	private String msg;
	
	public Package(PackageType type, String msg) {
		this.type = type;
		this.msg = msg;
	}
	
	public Package(PackageType type) {
		this.type = type;
	}
	
	public Package(String msg) {
		this.msg = msg;
	}
	
	public Package() {}
	
	public PackageType getPackageType() {return type;}
	public String getMessage() {return msg;}
	
	public void setPackageType(PackageType type) {this.type = type;}
	public void setMessage(String msg) {this.msg = msg;}
	
	@Override
	public String toString() {
		return "[package id: " + type.getId() + "] [message: " + msg + "]";
	}

}
