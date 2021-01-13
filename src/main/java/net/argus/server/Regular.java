package net.argus.server;

import net.argus.util.ArrayManager;

public enum Regular {
	
	a("@a"), s("@s");
	
	String value;
	
	private Regular(String value) {
		this.value = value;
	}
	
	public static boolean isExist(String enter) {
		return new ArrayManager<Regular>().convert(Regular.values()).contains(getRegular(enter));
	}
	
	public static Regular getRegular(String enter) {
		for(Regular reg : values())
			if(reg.value.equals(enter))
				return reg;
		return null;
	}
	
	@Override
	public String toString() {
		return value;
	}

}
