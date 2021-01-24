package net.argus.http;

public enum HTTPCode {
	
	c_200(200, "OK"), c_404(404, "Not Found");
	
	int code;
	String info;
	
	HTTPCode(int code, String info) {
		this.code = code;
		this.info = info;
	}
	
	public int getCode() {return code;}
	public String getInfo() {return info;}
	
	public String getMessage() {return code + " " + info;}

}
