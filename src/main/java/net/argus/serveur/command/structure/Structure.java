package net.argus.serveur.command.structure;

import java.util.ArrayList;
import java.util.List;

public class Structure {
	
	private List<String> strs = new ArrayList<String>();
	
	public Structure add(String str) {strs.add(str); return this;}
	
	public int getSize() {return strs.size();}
	
	public String getStucture() {
		String result = "/";
		
		for(int i = 0; i < strs.size(); i++)
			result += "<" + strs.get(i) + "> ";
		result = result.substring(0, result.length() - 1);
		
		return result;
	}

}