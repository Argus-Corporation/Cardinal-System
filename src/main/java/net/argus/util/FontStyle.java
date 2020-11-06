package net.argus.util;

public enum FontStyle {
	
	PLAIN("plain", 0), BOLD("bold", 1), ITALIC("italic", 2);
	
	
	int id;
	String name;
	
	FontStyle(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {return name;}
	public int getId() {return id;}
	
	public static FontStyle getStyle(int id) {
		for(FontStyle style : FontStyle.values())
			if(style.id == id) return style;
		return null;
	}
	
	public static FontStyle getStyle(String name) {
		for(FontStyle style : FontStyle.values())
			if(style.name.equals(name)) return style;
		return null;
	}

}
