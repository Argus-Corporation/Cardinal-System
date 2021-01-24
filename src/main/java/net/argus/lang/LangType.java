package net.argus.lang;

public enum LangType {
	
	en_US("en_US", "english", DefaultLangValue.en_US, 0), fr_FR("fr_FR", "français", DefaultLangValue.fr_FR, 1);
	
	private String name;
	private String relName;
	private DefaultLangValue defaultLangValue;
	private int id;
	
	LangType(String name, String relName, DefaultLangValue defaultLangValue, int id) {
		this.name = name;
		this.relName = relName;
		this.defaultLangValue = defaultLangValue;
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
	public DefaultLangValue getDefaultLangValue() {return defaultLangValue;}
	public int getId() {return id;}

}
