package net.argus.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import net.argus.annotation.Valid;

class ServerProxy {
	
	private BufferedReader in;
	private PrintWriter out;
	
	public ServerProxy(Socket server) throws IOException {
		in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		out = new PrintWriter(server.getOutputStream());
	}
	
	@Valid
	public void sendHeader(String[] header) {
		for(String line : header)
			out.println(line);
		
		/*for(String line : header)
			System.out.println(line);*/
	//	out.println("");
		out.flush();
	}
	
	@Valid
	public String[] nextHeader() throws IOException {
		List<String> header = new ArrayList<String>();
		String line;
		while(!(line = in.readLine()).equals("")) {
			header.add(line);
		}
		header.add("");
		/*System.out.println("Header (200) [");
		for(String str : header) {
			System.out.println(str);
		}
		
		System.out.println("]");*/
		
		return (String[]) header.toArray(new String[header.size()]);
	}
	
	public String[] nextValue() throws IOException {
		List<String> value = new ArrayList<String>();
		String line;
		while((line = in.readLine()) != null/* && !line.equals("")*/) {
			System.out.println(line);
			value.add(line);
		}
		value.add("");
		/*System.out.println("Value (200) [");

		for(String str : value) {
			System.out.println(str);
		}
		System.out.println("]");*/

		return (String[]) value.toArray(new String[value.size()]);
	}
	
	public String[] nextRequest() throws IOException {
		List<String> value = new ArrayList<String>();
		String line;
		while((line = in.readLine()) != null) {
			System.out.println(line);
			value.add(line);
		}
		value.add("");
		return (String[]) value.toArray(new String[value.size()]);
		//return (String[]) ArrayManager.add(nextHeader(), nextValue());
	}
	
	public static String[] connect(Socket server, String[] header) throws IOException {
		ServerProxy proxy = new ServerProxy(server);
		proxy.sendHeader(header);
		String[] req = proxy.nextRequest();
		
		return req;
	}

}
