package net.argus.util;

public class Version {
	
	private Type type = Type.RELEASE;
	private String version = "";
	
	public Version(String version) {
		this(version, Type.RELEASE);
	}
	
	public Version(String version, Type type) {
		this.type = type;
		if(isValid(version)) {
			DoubleStock<String, Type> ds = valueOf(version);
			this.version = ds.getFirst();
			this.type = ds.getSecond();
		}else
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
		
	public static DoubleStock<String, Type> valueOf(String version) {
		if(version == null || version.equals(""))
			return new DoubleStock<String, Version.Type>("0.0.0", Type.RELEASE);
		
		String ty = "";
		for(int i = version.length() - 1; i >= 0; i--) {
			char ch = version.charAt(i);
			
			if(CharacterManager.isNumber(ch) || ch == '.')
				break;
			
			version = version.substring(0, version.length()-1);
			
			ty = ch + ty;
		}
		Type type = Type.getType(ty);

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
		return new DoubleStock<String, Version.Type>(version, type);
	}
	
	public static boolean isValid(String version) {
		if(version.length() <= 0)
			return false;
		
		if(version.charAt(0) == '.')
			return false;
		
		if(CharacterManager.isNumber(version.charAt(version.length() - 1))) {
			for(char ch : version.toCharArray())
				if(!CharacterManager.isNumber(ch) && ch != '.')
					return false;
		}else {
			boolean type = false;
			for(int i = version.length() - 1; i >= 0; i--) {
				char ch = version.charAt(i);
				if(CharacterManager.isNumber(ch))
					type = true;
					
				if(!type && i == 0)
					return false;
				
				if(type && !CharacterManager.isNumber(ch) && ch != '.')
					return false;
			}
		}
		return true;
	}
	
	public String getVersion() {return version;}
	
	public Type getType() {return type;}
	
	@Override
	public String toString() {
		return version + type.getSuffix();
	}
	
	public enum State {
		SUPERIOR,
		INFERIOR,
		EQUALS;
	}
	
	public enum Type {
		ALPHA("a"),
		BETA("b"),
		RELEASE_CANDIDATE("rc"),
		RELEASE(null);
		
		private String suf;
		
		private Type(String suf) {
			if(suf == null)
				suf = "";
			
			this.suf = suf;
		}
		
		public String getSuffix() {return suf;}
		
		public static Type getType(String suf) {
			for(Type t : values())
				if(t.suf.equals(suf))
					return t;
			
			return RELEASE;
		}
	}
	
}
