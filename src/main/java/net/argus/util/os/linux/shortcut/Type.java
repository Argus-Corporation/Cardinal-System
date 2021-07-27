package net.argus.util.os.linux.shortcut;

@Deprecated
public enum Type {
	
	APPLICATION("Application"), LINK("Link"), DIRECTORY("Directory");
	
	private String name;
	
	private Type(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
