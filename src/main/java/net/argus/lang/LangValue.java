package net.argus.lang;

public class LangValue {
	
	private String key, value;
	
	public LangValue(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public LangValue() {}

	public String getKey() {return key;}
	public String getValue() {return value;}

	public void setKey(String key) {this.key = key;}
	public void setValue(String value) {this.value = value;}
	
	

}
