package net.argus.util.pack;

import net.argus.file.cjson.CJSONFile;
import net.argus.file.cjson.CJSONObject;
import net.argus.system.InitializationSystem;
import net.argus.system.UserSystem;
import net.argus.util.ErrorCode;

public class Package {
	
	private PackageBuilder builder;
	
	private int type;
	
	public Package(PackageBuilder builder) {
		this.builder = builder;
		this.builder.build();
		
		this.type = Integer.valueOf(String.valueOf(this.builder.getManifest().getValue("type")));
	}
	
	public int getType() {return type;}
	
	public String getValue(String name) {
		return builder.getPackage().getValue(name).toString();
	}
	
	public CJSONObject[] getArray(String name) {
		return builder.getPackage().getArrayValue(name);
	}
	
	public CJSONObject getObject(String name) {
		return builder.getPackage().getValue(name);
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
		InitializationSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		/*PackageBuilder bui = new PackageBuilder(0);
		bui.addManifestValue("id", "0");
		bui.addManifestValue("version", "11066");
		
		bui.addValue("file", "1000010001010101010100101010110101001001010101101000010110001001010");
		bui.addItemArray(new CJSONItemArray("test", new String[] {"test", "lol", "byebye"}));
		
		Package pack = new Package(bui);
		System.out.println(pack);*/
		//System.out.println("\"package\": {\"manifest\": {\"type\": 2}, \"pseudo\": \"Olario\"}");
		CJSONFile file = new CJSONFile("manifest", "");
		System.out.println(PackagePareser.parse(file.getFile()[0]).getValue("pseudo") + "  result");
	}

}
