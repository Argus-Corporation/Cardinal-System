package net.argus.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.argus.file.FileManager;
import net.argus.system.UserSystem;

public class HTTPClient {
	
	private Socket client;
	
	private BufferedReader read;
	private PrintWriter writer;
	
	private HTTPHeader header;
	
	private boolean running = true;
	
	public HTTPClient(Socket client) throws IOException {
		this.client = client;
		
		read = new BufferedReader(new InputStreamReader(client.getInputStream()));
		writer = new PrintWriter(client.getOutputStream());
		
		header = new HTTPHeader();
		
		new Thread(receiveRequest()).start();
	}
	
	public Runnable receiveRequest() {
		return new Runnable() {
			public void run() {
				try {
					while(running) {
						String msg = read.readLine();
						System.out.println(msg);
						if(msg == null) break;
						analyzerHeaderRequest(msg);
					}
					
					close();
				}catch(IOException e) {}
				
			}
		};
		
	}
	
	public void analyzerHeaderRequest(String msg) throws IOException {
		if(!msg.equals(""))
			header.analyzer(msg);
		else
			reply();
	}
	
	public void reply() throws IOException {
		File openFile = getFile();
		HTTPCode code = getCode(openFile);
		
		BufferedReader dataIn = getStream(openFile);
		
		addHeader(writer, dataIn, code);
		addFile(writer, dataIn, code);
		
		writer.flush();
		
		if(header.get("Connection").equals(HTTPConnection.close.toString())) {
			return;
		}
	running = false;
		
	}
	
	public void addHeader(PrintWriter writer, BufferedReader dataIn, HTTPCode code) {
		writer.println("HTTP/1.1" + " " + code.getMessage());
		writer.println("Content-Type: " + header.get("Accept").split(",")[0] + "; charset=UTF-8");
		writer.println("Connection: close");
		writer.println("Server: Cardinal");
		writer.println();
	}
	
	public void addFile(PrintWriter writer, BufferedReader dataIn, HTTPCode code) throws IOException {
		if(code == HTTPCode.c_404)
			dataIn = getStream(new File(UserSystem.getProperty("http.path.404")));
		
		println(writer, dataIn);
	}
	
	public void println(PrintWriter writer, BufferedReader dataIn) throws IOException {
		String line;
		while((line = dataIn.readLine()) != null)
			writer.println(line);
	}
	
	public HTTPCode getCode(File file) {
		HTTPCode code = HTTPCode.c_200;

		if(!file.exists())
			code = HTTPCode.c_404;
		
		return code;
	}
	
	public BufferedReader getStream(File file) throws FileNotFoundException {
		BufferedReader dataIn = null;
		
		if(file.exists())
			dataIn = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		return dataIn;
	}
	
	public File getFile(String fileName) {
		return new File(FileManager.getMainPath() + "/www" + fileName);
	}
	
	public File getFile() {
		return header.getHTTPFile().getFile();
	}
	
	public void close() throws IOException {
		read.close();
		writer.close();
		client.close();
	}

}