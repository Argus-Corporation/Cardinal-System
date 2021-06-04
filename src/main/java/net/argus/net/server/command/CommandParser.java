package net.argus.net.server.command;

import java.util.ArrayList;
import java.util.List;

import net.argus.net.server.command.structure.KeyType;
import net.argus.net.server.command.structure.Structure;
import net.argus.net.server.command.structure.StructuredCommand;
import net.argus.net.server.command.structure.StructuredKey;
import net.argus.util.StringManager;

public class CommandParser {
	
	public static StructuredCommand structureCommand(String strCom, Structure struc) {
		List<StructuredKey> keys = new ArrayList<StructuredKey>();
		String[] com = strCom.split(" ");
		com = StringManager.valueOf(com);
		
		if(com.length < struc.length())
			return null;
		
		for(int i = 1; i < com.length && i < struc.size() - 1; i++) {
			KeyType type = getType(struc, i);
			if(!keyIsValid(com[i], type))
				return null;
			
			keys.add(new StructuredKey(com[i], type));
		}
		
		if(com.length >= struc.size()) {
			String last = "";
			for(int i = struc.size() - 1; i < com.length; i++)
				last += com[i] + " ";
			last = last.substring(0, last.length() - 1);
		
			keys.add(new StructuredKey(last, getType(struc, struc.size() - 1)));
		}
		
		
		return new StructuredCommand((StructuredKey[]) keys.toArray(new StructuredKey[keys.size()]));
	}
	
	private static KeyType getType(Structure structure, int i) {return structure.getKey(i).getType();}
	
	private static boolean keyIsValid(String k, KeyType type) {
		switch(type) {
	 		case STRING:
	 			return true;
	 		case INT:
	 			return StringManager.isInteger(k);
	 		case BOOLEAN:
	 			return StringManager.isBoolean(k);
		}
		return false;
	}

}
