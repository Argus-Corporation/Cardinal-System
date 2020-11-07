package net.argus.lang;

public enum LangType {
	
	en_US("en_US", 0), fr_FR("fr_FR", 1);
	
	private final String name;
	private final int id;
	
	LangType(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public static LangType getLangType(int id) {
		LangType[] lts = values();
		
		for(LangType lt : lts)
			if(id == lt.getId()) return lt;
			
		return en_US;
	}
	
	public static LangType getLangType(String name) {
		LangType[] lts = values();
		
		for(LangType lt : lts)
			if(name.equals(lt.getName())) return lt;
			
		return en_US;
	}
	
	public String getName() {return name;}
	public int getId() {return id;}

}
