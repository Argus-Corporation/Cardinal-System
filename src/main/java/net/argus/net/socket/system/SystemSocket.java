package net.argus.net.socket.system;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import net.argus.net.pack.Package;
import net.argus.net.socket.CardinalSocket;

public class SystemSocket extends CardinalSocket {
	
	private PrintStream out;
	
	public SystemSocket() {
		super();
		connect = true;
	}

	@Override
	public void connect(String host, int port) throws UnknownHostException, IOException {}

	@Override
	public Object[] getInfos() {return null;}

	@Override
	public void setInfos(Object[] infos) {}

	@Override
	public Package nextPackage() throws IOException {
		return null;
	}
/*
	@Override
	public void send(Package pack) throws IOException {
		if(out == null || pack == null || pack.isNull())
			return;
		
		Printer.printPackage(pack, out);
	}
*/
	@Override
	public CardinalSocket create() throws IOException {
		return new SystemSocket();
	}

	@Override
	public CardinalSocket create(Socket socket) throws IOException {
		return create();
	}
	
	public void setOutput(PrintStream out) {this.out = out;}
	public PrintStream getOutput() {return out;}
	
	@Override
	public InetAddress getInetAddress() {
		try {return InetAddress.getLocalHost();
		}catch (UnknownHostException e) {}
		return null;
	}
	
	@Override
	public synchronized void close(boolean error, String arg) throws IOException {
  		connect = false;
  		if(out != System.out) out.close();
	}

	@Override
	public void send(Object pack) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
