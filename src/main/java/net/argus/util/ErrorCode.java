package net.argus.util;

public enum ErrorCode {
	
	leave("leave", -1, 1), error("error", 0, 0), kick("kick", 12, 1), ban("ban", 5, 0), crypt("crypt", 1, 1), full("full", 7, 1),
	coruptVersion("version", 87, 0), version("version", 85, 0);
	
	String name;
	int code, typeMessage;
	
	private ErrorCode(String name, int code, int typeMessage) {
		//this.name = "info." + name + ".name";
		this.name = name;
		this.code = code;
		this.typeMessage = typeMessage;
	}
	
	public static ErrorCode valueOf(int code) {
		for(ErrorCode ec : values())
			if(code == ec.code)
				return ec;
		
		return error;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCode() {return code;}
	public int getMessageType() {return typeMessage;}
	
	@Override
	public String toString() {
		return getName() + ": " + code;
	}

}
