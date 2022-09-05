package net.argus.beta.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;

import net.argus.beta.net.pack.Package;
import net.argus.beta.net.pack.PackageReturn;

public class Stream {
	
	protected BufferedReader in;
	protected PrintWriter out;
	
	public Stream(SSLSocket sock) throws IOException {
		this(sock.getInputStream(), sock.getOutputStream());
	}
	
	public Stream(InputStream in, OutputStream out) throws IOException {
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = new PrintWriter(out);
	}
		
	public String nextLine() throws IOException {
		return in.readLine();
	}
	
	public PackageReturn nextPackage() throws IOException {
		String line = in.readLine();
		if(line == null)
			return null;
		System.out.println("pack  " + line);
		return new PackageReturn(line);
	}
	
	public void send(Package pack) {
		System.out.println("send  " + pack);
		out.println(pack);
		out.flush();
	}
	
	public void close() throws IOException {
		in.close();
		out.close();
	}

}
