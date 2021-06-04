package net.argus.net.socket;

public enum SocketStatus {
	
	TIME_OUT("timeout"), UNKNOWNHOST("unknownhost"), CLOSE("close"), CONNECTED("connected"), DISCONNECTED("disconnected");
	
	private String normalizedName;
	
	private SocketStatus(String normalizedName) {
		this.normalizedName = normalizedName;
	}
	
	public String getNormalizedName() {return normalizedName;}
	
}
