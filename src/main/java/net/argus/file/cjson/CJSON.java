package net.argus.file.cjson;

import java.util.List;

import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;

public class CJSON {
	
	List<CJSONObject> objs;
	
	public CJSON(List<CJSONObject> objs) {
		this.objs = objs;
	}
	
	public CJSONObject getObject(String name) {return getObject(new CJSONString(name));}
	
	public CJSONObject getObject(CJSONString name) {
		for(CJSONObject obj : objs)
			if(obj.getName().equals(name)) return obj;
		return null;
	}
	
	public static void main(String[] args) {
		InitializedSystem.initSystem(new String[] {"-project.name", "Cardinal-System", "-id", "0xdev"}, UserSystem.getDefaultInitializedSystemManager());
		
		System.out.println(CJSONPareser.parse(new CJSONFile("config", "")).getObject("form").getArrayValue("data")[2]);
		
		UserSystem.exit(0);
	}


}
