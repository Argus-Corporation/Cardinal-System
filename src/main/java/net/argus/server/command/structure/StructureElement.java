package net.argus.server.command.structure;

public class StructureElement {
	
	private String name;
	private boolean optional;
	
	public StructureElement(String name) {
		this(name, false);
	}
	
	public StructureElement(String name, boolean optional) {
		this.name = name;
		this.optional = optional;
	}
	
	public String getName() {return name;}
	public boolean isOptional() {return optional;}
	
	@Override
	public String toString() {
		return name; 
	}

}
