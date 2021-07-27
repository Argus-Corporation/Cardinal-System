package net.argus.net.client;

import java.io.IOException;
import java.net.UnknownHostException;

import net.argus.event.net.process.ProcessListener;
import net.argus.event.net.socket.SocketListener;
import net.argus.net.Connection;
import net.argus.net.Process;
import net.argus.net.Profile;
import net.argus.net.pack.Package;
import net.argus.net.socket.CardinalSocket;
import net.argus.net.socket.SocketStatus;
import net.argus.util.Version;
import net.argus.util.debug.Debug;
import net.argus.util.debug.Info;

public class Client {
	
	public static final Version VERSION = new Version("1.1");
	
	private Connection clientConnect;
	private Process clientProcess;
	
	private CardinalSocket socket;
	
	private String host;
	private int port;
	
	public Client(String host, int port, CardinalSocket socket) {
		this.socket = socket;
		this.host = host;
		this.port = port;
		
		clientProcess = new ClientProcess();	
		clientConnect = new ClientConnection(clientProcess);
	}
	
	public void connect(String password) throws IOException {
		try {socket.connect(host, port);}
		catch(IOException e) {
			SocketStatus status;
			
			String arg;
			if(e instanceof UnknownHostException) {
				arg = "unknownhost";
				Debug.log(arg, Info.ERROR);
				status = SocketStatus.UNKNOWNHOST;
			}else {
				arg = "exception";
				Debug.log(arg, Info.ERROR);
				status = SocketStatus.TIME_OUT;
			}
			socket.setConnected(false, true, arg, status);
			throw e;
		}
		
		socket.getProfile().setPassword(password);
		clientConnect.connect(socket);
	}
	
	public void logOut(String arg) throws IOException {
		clientProcess.close();
		
		socket.logOut(arg);
	}
	
	public void addProcessListener(ProcessListener listener) {clientProcess.addProcessListener(listener);}
	public void addSocketListener(SocketListener listener) {socket.addSocketListener(listener);}
	
	public ClientProcess getProcess() {return (ClientProcess) clientProcess;}
	public CardinalSocket getCardinalSocket() {return socket;}
	public Profile getProfile() {return socket.getProfile();}
	
	public void send(Package pack) {try {socket.send(pack);}catch(IOException e) {e.printStackTrace();}}
	
	public boolean isConnected() {return socket.isConnected();}
	
}
