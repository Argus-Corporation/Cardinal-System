package net.argus.server.command.structure;

import java.util.ArrayList;
import java.util.List;

public class Structure {
	
	private List<StructureElement> eles = new ArrayList<StructureElement>();
	
	public Structure() {
		add("command");
	}
	
	public Structure add(String name) {eles.add(new StructureElement(name)); return this;}
	public Structure add(String name, boolean optional) {eles.add(new StructureElement(name, optional)); return this;}
	
	public int getSize() {
		int length = 0;
		for(StructureElement element : eles)
			if(!element.isOptional())
				length++;
		
		return length;
	}
	
	public String getStucture() {
		String result = "/";
		
		for(int i = 0; i < eles.size(); i++)
			result += "<" + eles.get(i) + "> ";
		result = result.substring(0, result.length() - 1);
		
		return result;
	}

}