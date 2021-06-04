package net.argus.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.argus.annotation.Valid;
import net.argus.util.ThreadManager;

public class ClientProxy {
		
	private BufferedReader in;
	private PrintWriter out;
	
	public ClientProxy(Socket client) throws IOException {
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream());
	}
	
	@Valid
	public String[] nextRequest() throws IOException {
		String line;
		
		List<String> request = new ArrayList<String>();
		while((line = in.readLine()) != null && !line.equals("")) {
			request.add(line);
		}
		request.add("");
		
		/*for(String str : request)
			System.out.println(str);
		*/
		return (String[]) request.toArray(new String[request.size()]);
	}
	
	public void sendRequest(String[] request) {
		/*for(String line : request)
			out.println(line);*/
		/*for(String line : request)
			System.out.println(line);*/
		out.println("");
		out.flush();
	}
	
	private static void process(Socket client) throws IOException {
		ClientProxy proxy = new ClientProxy(client);
			String[] header = proxy.nextRequest();
		
		@Valid
		URL url = RequestAnalizer.getURL(header);
		
		proxy.sendRequest(ServerProxy.connect(new Socket(url.getHost(), url.getProtocol().equals("http")?80:81), RequestAnalizer.valueOf(header)));
		
		//client.close();
	}
	
	public static void connect(ServerSocket proxy) {
		new ThreadManager("proxy connection").start(new Runnable() {
			public void run() {
				try {
				while(true)
						ClientProxy.process(proxy.accept());
				}catch(IOException e) {e.printStackTrace();}
			}
		});
	}

}
