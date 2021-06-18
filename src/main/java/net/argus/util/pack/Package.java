package net.argus.util.pack;

import net.argus.file.cjson.CJSONObject;
import net.argus.system.InitializationSystem;
import net.argus.util.ErrorCode;

@SuppressWarnings("deprecation")
public class Package {
	
	private PackageBuilder builder;
	
	private int type;
	
	public Package(PackageBuilder builder) {
		this.builder = builder;
		this.builder.build();
		
		this.type = Integer.valueOf(String.valueOf(this.builder.getManifestValue("type")));
	}
	
	public int getType() {return type;}
	
	public String getValue(String name) {
		return builder.getValue(name).toString();
	}
	
	public CJSONObject[] getArray(String name) {
		return builder.getArray(name);
	}
	
	public CJSONObject getObject(String name) {
		return builder.getValue(name);
	}
	
	public static Package getErrorPackage() {
		PackageBuilder bui = new PackageBuilder(PackageType.LOG_OUT);
		
		bui.addItem(new PackageItem("message", "error"));
		bui.addItem(new PackageItem("code", String.valueOf(ErrorCode.error.getCode())));
		
		return new Package(bui);
	}

	public static Package getLeavePackage() {
		PackageBuilder bui = new PackageBuilder(PackageType.LOG_OUT);
		
		bui.addItem(new PackageItem("message", "leave"));
		bui.addItem(new PackageItem("code", String.valueOf(ErrorCode.leave.getCode())));
		
		return new Package(bui);
	}
	
	@Override
	public String toString() {
		return builder.getFile();
	}
	
	public static void main(String[] args) {
		InitializationSystem.initSystem(args);
		PackageBuilder bui = new PackageBuilder(5845);
		
		Package pack = new Package(bui);
		System.out.println(pack);
	}

}
