package net.argus.net.server.command.structure;

public class StructuredCommand {
	
	private StructuredKey[] keys;
	
	public StructuredCommand(StructuredKey[] keys) {
		this.keys = keys;
	}
	
	public int length() {return keys.length;}
	
	public StructuredKey[] getKeys() {return keys;}
	public StructuredKey getKey(int index) {return keys[index];}
	
	public Object get(int index) {return getKey(index).getValue();}

}
