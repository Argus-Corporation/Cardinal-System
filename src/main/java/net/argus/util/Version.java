package net.argus.util;

public class Version {
	
	private String version = "";
	
	public Version(String version) {
		if(isValid(version))
			this.version = valueOf(version);
		else
			throw new IllegalArgumentException(version);
	}
	
	public State getState(Version other) {
		String[] decompVers = version.split("\\.");
		
		int first = Integer.valueOf(decompVers[0]);
		int second = Integer.valueOf(decompVers[1]);
		int vers =  Integer.valueOf(decompVers[2]);
		
		String[] decompOtherVers = other.getVersion().split("\\.");

		int otherFirst = Integer.valueOf(decompOtherVers[0]);
		int otherSecond = Integer.valueOf(decompOtherVers[1]);
		int otherVers = Integer.valueOf(decompOtherVers[2]);

		if(first < otherFirst)
			return State.INFERIOR;
		if(first > otherFirst)
			return State.SUPERIOR;
		if(second < otherSecond)
			return State.INFERIOR;
		if(second > otherSecond)
			return State.SUPERIOR;
		if(vers < otherVers)
			return State.INFERIOR;
		if(vers > otherVers)
			return State.SUPERIOR;
		
		if(vers == otherVers)
			return State.EQUALS;
		
		return State.EQUALS;
	}
	
	public String getVersion() {return version;}
	
	public static String valueOf(String version) {
		if(version == null || version.equals(""))
			return "0.0.0";
			
		while(version.endsWith("."))
			version = version.substring(0, version.length()-1);
		
		while(version.contains(".."))
			version = StringManager.remplace(version, version.indexOf(".."), 2, ".");
		
		String[] decompVers = version.split("\\.");
		
		if(decompVers.length < 3) {
			for(int i = decompVers.length; i < 3; i++)
				version += ".0";
		}
		
		if(decompVers.length > 3) {
			for(int i = 3; i < decompVers.length; i++)
				version = StringManager.remplace(version, version.lastIndexOf("."), 1, "");
		}
		return version;
	}
	
	public static boolean isValid(String version) {
		for(char ch : version.toCharArray())
			if(!CharacterManager.isNumber(ch) && ch != '.')
				return false;
		return true;
	}
	
	@Override
	public String toString() {
		return version;
	}
	
	public enum State {
		SUPERIOR,
		INFERIOR,
		EQUALS;
	}

}
