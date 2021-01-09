package net.argus.server.command;

public enum Regular {
	
	a("@a"), s("@s");
	
	String value;
	
	private Regular(String value) {
		this.value = value;
	}
	
	public static Regular getRegular(String value) {
		for(Regular reg : values())
			if(value.equals(reg.toString()))
				return reg;
		
		return null;
	}
	
	public static boolean isRegular(String value) {
		for(Regular reg : values())
			if(value.equals(reg.toString()))
				return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		return value;
	}

}
