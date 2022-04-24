package net.argus.lang;

import java.util.ArrayList;
import java.util.List;

public class LangType {
	
	private static List<LangType> values = new ArrayList<LangType>();
	
	public static final LangType en_US = new LangType("en_US", "english", DefaultLangValue.en_US);
	public static final LangType fr_FR = new LangType("fr_FR", "français", DefaultLangValue.fr_FR);
	public static final LangType ja_JP = new LangType("ja_JP", "日本人", DefaultLangValue.ja_JP);
	public static final LangType es_ES = new LangType("es_ES", "español", DefaultLangValue.es_ES);
	
	private String name;
	private String realName;
	private DefaultLangValue defaultLangValue;
	private int id;
	
	public LangType(String name, String realName, DefaultLangValue defaultLangValue) {
		this.name = name;
		this.realName = realName;
		this.defaultLangValue = defaultLangValue;
		this.id = values.size();
		
		values.add(this);
	}
	
	public static LangType getLangType(int id) {
		for(LangType lt : values)
			if(id == lt.getId()) return lt;
			
		return en_US;
	}
	
	public static LangType getLangType(String name) {
		for(LangType lt : values)
			if(name.equals(lt.getName())) return lt;
			
		return en_US;
	}
	
	public static LangType getLangType(String realName, int nul) {
		for(LangType lt : values)
			if(realName.equals(lt.getRealName())) return lt;
		
		return en_US;
	}
	
	public String getName() {return name;}
	public String getRealName() {return realName;}
	public DefaultLangValue getDefaultLangValue() {return defaultLangValue;}
	public int getId() {return id;}
	
	@Override
	public String toString() {
		return "lang@[" + "name: " + name + ", realname: " + realName + ", id: " + id + "]";
	}

}
