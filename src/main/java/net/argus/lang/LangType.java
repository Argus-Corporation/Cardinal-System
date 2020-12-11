package net.argus.lang;

public enum LangType {
	
	en_US("en_US", "english", 0), fr_FR("fr_FR", "français", 1);
	
	private String name;
	private String relName;
	private int id;
	
	LangType(String name, String relName, int id) {
		this.name = name;
		this.relName = relName;
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
	
	public static LangType getLangType(String relName, int nul) {
		LangType[] lts = values();
		
		for(LangType lt : lts)
			if(relName.equals(lt.getRelName())) return lt;
		
		return en_US;
	}
	
	public String getName() {return name;}
	public String getRelName() {return relName;}
	public int getId() {return id;}

}
