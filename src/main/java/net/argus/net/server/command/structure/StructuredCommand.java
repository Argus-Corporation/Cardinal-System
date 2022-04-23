package net.argus.net.server.command.structure;

import net.argus.util.ArrayManager;

public class StructuredCommand {
	
	private StructuredKey[] keys;
	
	public StructuredCommand(StructuredKey[] keys) {
		this.keys = keys;
	}
	
	public int length() {return keys.length;}
	
	public StructuredKey[] getKeys() {return keys;}
	public StructuredKey getKey(int index) {return keys[index];}
	
	public Object get(int index) {return getKey(index).getValue();}
	
	@Override
	public String toString() {
		return ArrayManager.toList(keys).toString();
	}

}
