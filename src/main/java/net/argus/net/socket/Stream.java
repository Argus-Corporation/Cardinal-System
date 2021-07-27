package net.argus.net.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import net.argus.net.pack.PackageAnalizer;
import net.argus.net.pack.key.PackageKey;

public class Stream {
	
	private BufferedReader in;
	
	private PrintWriter out;
	
	public Stream(Socket sock) throws IOException {
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		out = new PrintWriter(sock.getOutputStream());
	}
	
	public String nextLine() throws IOException {
		return in.readLine();
	}
	
	public List<PackageKey> nextPackage() throws IOException {
		List<String> lines = new ArrayList<String>();
		
		String line;
		while((line = nextLine()) != null && !line.equals(""))
			lines.add(line);
		
		return PackageAnalizer.convertToKey(lines);
	}
	
	public void send(Object pack) {
		out.println(pack);
		out.flush();
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
	}

}
