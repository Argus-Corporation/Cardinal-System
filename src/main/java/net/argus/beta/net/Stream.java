package net.argus.beta.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.ctp.CtpURLConnection;
import net.argus.beta.net.pack.Package;
import net.argus.beta.net.pack.PackageReturn;

public class Stream {
	
	protected BufferedReader in;
	protected PrintWriter out;
	
	public Stream(CtpURLConnection connection) throws IOException {
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		out = new PrintWriter(connection.getOutputStream());
	}
	
	public Stream(SSLSocket sock) throws IOException {
		in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		out = new PrintWriter(sock.getOutputStream());
	}
		
	public String nextLine() throws IOException {
		return in.readLine();
	}
	
	public PackageReturn nextPackage() throws IOException {
		String line = in.readLine();
		if(line == null)
			return null;
		return new PackageReturn(line);
	}
	
	public void send(Package pack) {
		out.println(pack);
		out.flush();
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
	}

}
