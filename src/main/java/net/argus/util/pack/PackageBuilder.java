package net.argus.util.pack;

import net.argus.file.cjson.CJSON;
import net.argus.file.cjson.CJSONBuilder;
import net.argus.file.cjson.CJSONItem;
import net.argus.file.cjson.CJSONItemArray;
import net.argus.file.cjson.CJSONObject;
import net.argus.file.cjson.CJSONString;

public class PackageBuilder extends CJSONBuilder {
	
	private CJSONObject mainObj, manifest;
	
	public PackageBuilder(CJSON cjson) {
		this.mainObj = cjson.getObject("package");
		this.manifest = mainObj.getValue("manifest");
	}
	
	public PackageBuilder(int type) {
		mainObj = new PackageObject("package");
		manifest = new PackageObject("manifest");
		mainObj.addItem(new PackageItem("manifest", manifest));
		
		manifest.addItem(new PackageItem("type", String.valueOf(type)));
		
		addObject(mainObj);
	}
	
	public PackageBuilder(PackageType type) {
		this(type.getId());
	}
	
	public PackageBuilder addManifestValue(String name, String value) {addManifestValue(name, new CJSONString(value)); return this;}
	public PackageBuilder addManifestValue(String name, CJSONObject value) {manifest.addItem(new PackageItem(name, value)); return this;}
	
	public PackageBuilder addValue(String name, String value) {addValue(name, new CJSONString(value)); return this;}
	public PackageBuilder addValue(String value) {addValue(new CJSONString(value)); return this;}
	public PackageBuilder addValue(String name, CJSONObject value) {mainObj.addItem(new CJSONItem(name, value)); return this;}
	public PackageBuilder addValue(CJSONObject value) {mainObj.addItem(new CJSONItem(value.getName(), value)); return this;}
	
	public PackageBuilder addItem(CJSONItem item) {mainObj.addItem(item); return this;}
	public PackageBuilder addItemArray(CJSONItemArray array) {mainObj.addItemArray(array); return this;}
	public PackageBuilder addItemArray(String name, String[] array) {addItemArray(new CJSONItemArray(name, array)); return this;}
	
	public CJSONObject getPackage() {return mainObj;}
	public CJSONObject getManifest() {return manifest;}

}
