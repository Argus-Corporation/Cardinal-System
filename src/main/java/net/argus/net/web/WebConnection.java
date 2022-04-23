package net.argus.net.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;

import net.argus.event.net.web.EventWeb;
import net.argus.event.net.web.WebEvent;
import net.argus.util.Error;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class WebConnection extends Thread {
	
	public static final String MAGIC_KEY = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
	
	private WebServer parent;
	private Socket client;
	
	private InputStream in;
	private OutputStream out;
	
	public WebConnection(Socket client, WebServer parent) throws IOException {
		this.client = client;
		this.parent = parent;
		in = client.getInputStream();
		out = client.getOutputStream();
	}
	
	@SuppressWarnings("resource")
	@Override
	public void run() {
		Debug.log("Starting web connection");
		try {
			Scanner scan = new Scanner(in, "UTF-8").useDelimiter("\\r\\n\\r\\n");
			if(!scan.hasNext())
				return;
			
			String data = scan.next();
			Matcher get = Pattern.compile("^GET").matcher(data);
	
			if(get.find()) {
				Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
				match.find();
				byte[] response = getPackageReponse(match);
	
				out.write(response, 0, response.length);
			}
			
			parent.getEvent().startEvent(EventWeb.ACCEPT, new WebEvent(client));
		}catch(IOException | NoSuchAlgorithmException e) {Error.createErrorFileLog(e); Debug.log("Error on accepting connection to web", Info.ERROR);}
	}
	
	private byte[] getPackageReponse(Matcher match) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return ("HTTP/1.1 101 Switching Protocols\r\n"
				+ "Connection: Upgrade\r\n"
				+ "Upgrade: websocket\r\n"
				+ "Sec-WebSocket-Accept: "
				+ DatatypeConverter.printBase64Binary(MessageDigest.getInstance("SHA-1").digest(
						(match.group(1) + MAGIC_KEY).getBytes("UTF-8")))
				+ "\r\n\r\n").getBytes("UTF-8");
	}

}
