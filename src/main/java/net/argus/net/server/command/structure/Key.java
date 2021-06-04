package net.argus.net.server.command.structure;

public class Key {
	
	private String name;
	
	private KeyType type;
	
	private boolean obligatory;
	
	public Key(String name, KeyType type, boolean obligatory) {
		this.name = name;
		this.type = type;
		this.obligatory = obligatory;
	}
	
	public Key(String name, KeyType type) {
		this(name, type, true);
	}

	public String getName() {return name;}
	public KeyType getType() {return type;}
	public boolean isObligatory() {return obligatory;}
	
	@Override
	public String toString() {
		String name = "<" + this.name + ">";
		if(!obligatory)
			return "[" + name + "]";
		else
			return name;
	}
}
