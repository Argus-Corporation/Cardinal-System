package net.argus.http;

import java.util.ArrayList;
import java.util.List;

import net.argus.util.ArrayManager;

public class HTTPHeader {
	
	private List<String> headerName = new ArrayList<String>();
	private List<String> header = new ArrayList<String>();
	
	private HTTPMethod method;
	private HTTPFile file;
	private String versionProtocol;
	
	public HTTPHeader() {
		file = new HTTPFile();
	}
	
	public void put(String name, String value) {
		headerName.add(name);
		header.add(value);
	}
	
	public String get(String name) {
		return header.get(headerName.indexOf(name));
	}
	
	public void analyzer(String msg) {
		String[] part = msg.split(": ");
		
		if(!ArrayManager.isExist(part, 1)) {
			part = msg.split(" ");
			method = HTTPMethod.valueOf(part[0]);
			
			if(part[1].indexOf('.') != -1) {
				file.setPath(part[1].substring(0, part[1].lastIndexOf('/')));
				file.setFileName(part[1].substring(part[1].lastIndexOf('/') + 1, part[1].lastIndexOf('.')));
				file.setExtention(part[1].substring(part[1].lastIndexOf('.') + 1));
			}else
				file.setPath(part[1]);
				
			versionProtocol = part[2];
			return;
		}else
			put(part[0], part[1]);
		
	}
	
	public HTTPMethod getMethod() {return method;}
	public HTTPFile getHTTPFile() {return file;}
	public String getVersion() {return versionProtocol;}
	
}
