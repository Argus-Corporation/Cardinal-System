package net.argus.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ExampleHTTP {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
		ServerSocket serv = new ServerSocket(80);
		
		Socket cli = serv.accept();
		
		BufferedReader read = new BufferedReader(new InputStreamReader(cli.getInputStream()));
		PrintWriter writer = new PrintWriter(cli.getOutputStream());
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					while(true) {
						String msg = read.readLine();
						if(msg!=null) {
							System.out.println(msg);
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}).start();
		//Thread.sleep(1000);
		
		
		String[] file = new String[] {"<!DOCTYPE html>",
				"<html lang=\"fr\">",
				"<head>",
				"<meta charset=\"utf-8\">",
				"<title>Voici mon site</title>",
				"</head>",
				"<body>",
				"<h1>Hello World! Ceci est un titre</h1>",
				"<p>Ceci est un <strong>paragraphe</strong>. Avez-vous bien compris ?</p>",
				"</body>",
				"</html>"};
		
		
		writer.println("HTTP/1.1 200 OK");
		writer.println("Date: Fri, 04 Dec 2020 14:00:18 GMT");
		writer.println("Server: Apache");
		writer.println("Vary: Accept-Encoding");
		//writer.println("Content-Encoding: br");
		//writer.println("Content-Length: " + file.length);
		writer.println("Keep-Alive: timeout=5, max=499");
		writer.println("Content-Type: text/html; charset=UTF-8");
		writer.println("Via: 1.1 alproxy");
		writer.println();

	
		
		for(String line : file) {
			//System.out.println(line);
			writer.println(line);
		}
		
		writer.flush();
		
		writer.println("");
		writer.flush();
		
		System.out.println("end");
	}

}
