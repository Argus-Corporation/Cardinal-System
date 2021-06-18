package net.argus.util.pack;

import net.argus.file.cjson.CJSON;
import net.argus.file.cjson.CJSONBuilder;
import net.argus.file.cjson.CJSONInteger;
import net.argus.file.cjson.CJSONItem;
import net.argus.file.cjson.CJSONObject;
import net.argus.file.cjson.CJSONString;

@SuppressWarnings("deprecation")
public class PackageBuilder extends CJSONBuilder {
	
	private CJSONObject packageObject; 
	private CJSONObject manifest;
	
	public PackageBuilder(CJSON cjson) {
		this.packageObject = cjson.getItem("package").getValue();
		this.manifest = packageObject.getItem("manifest").getValue();
	}
	
	public PackageBuilder(int type) {
		packageObject = new PackageObject();
		manifest = new PackageObject();
		
		manifest.addValue("type", new CJSONInteger(type));
		
		packageObject.addItem(new CJSONItem("manifest", manifest));
		addItem(new PackageItem("package", packageObject));
	}
	
	public PackageBuilder(PackageType type) {
		this(type.getId());
	}
	
	public PackageBuilder addManifestValue(String name, String value) {addManifestValue(name, new CJSONString(value)); return this;}
	public PackageBuilder addManifestValue(String name, CJSONObject value) {manifest.addItem(new PackageItem(name, value)); return this;}
	
	public PackageBuilder addValue(String name, String value) {addValue(name, new CJSONString(value)); return this;}
	public PackageBuilder addValue(String name, CJSONObject value) {addValue(new CJSONItem(name, value)); return this;}
	public PackageBuilder addValue(CJSONItem value) {packageObject.addItem(value); return this;}
	
	//public PackageBuilder addItem(CJSONItem item) {mainObj.addItem(item); return this;}
	/*public PackageBuilder addItemArray(CJSONArray array) {mainObj.addItemArray(array); return this;}
	public PackageBuilder addItemArray(String name, String[] array) {addItemArray(new CJSONArray(name, array)); return this;}
	*/
	
	public CJSONObject getValue(String name) {return packageObject.getValue(name);}
	public CJSONObject[] getArray(String name) {return packageObject.getArrayValue(name);}
	public CJSONObject getManifestValue(String name) {return manifest.getValue(name);}

	
	public CJSONObject getPackage() {return packageObject;}
	public CJSONObject getManifest() {return manifest;}

}
