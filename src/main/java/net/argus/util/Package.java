package net.argus.util;

public class Package {
	
	private int type;
	private String msg;
	
	public Package(PackageType type, String msg) {this(type.getId(), msg);}
	
	public Package(PackageType type) {this(type.getId());}
	
	public Package(int type) {this(type, null);}
	
	public Package(String msg) {this(0, msg);}
	
	public Package(int type, String msg) {
		this.type = type;
		this.msg = msg!=null?msg:"";
	}
	
	public Package() {this(0, null);}
	
	public int getType() {return type;}
	public String getMessage() {return msg;}
	
	public void setType(PackageType type) {this.type = type.getId();}
	public void setType(int type) {this.type = type;}
	public void setMessage(String msg) {this.msg = msg;}
	
	@Override
	public String toString() {
		return "[package id: " + type + "] [message: " + msg + "]";
	}

}
