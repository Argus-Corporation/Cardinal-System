package net.argus.system;

public enum ExitCode {
	
	VALID(0), ERROR(-1);
	
	private int code;
	
	private ExitCode(int code) {
		this.code = code;
	}
	
	public int getCode() {return code;}
	
	public static ExitCode valueOf(int code) {
		for(ExitCode ec : values())
			if(code == ec.code)
				return ec;
		return null;
	}

}
