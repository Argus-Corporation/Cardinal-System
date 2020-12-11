package net.argus.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import net.argus.exception.SecurityException;
import net.argus.security.Key;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageObject;
import net.argus.util.pack.PackagePareser;
import net.argus.util.pack.PackageType;

public class SocketClient {
	
	private Socket socket;
	private BufferedReader msgRecei;
	private PrintWriter msgSend;
	
	private String host;
	private int port;
	
	private Key key = null;
	
	private boolean serverUseKey = false;
	private boolean connected;
	
	private String pseudo;
	private String password;
	
	public SocketClient(String host, int port, Key key) throws IOException {
		this.host = host;
		this.port = port;
		
		this.key = key;
	}
	
	public SocketClient(String host, int port) throws IOException {
		this.host = host;
		this.port = port;
	}
	
	public void connect() throws UnknownHostException, IOException {
		socket = new Socket(host, port);
		
		Debug.log("Connected to " + host);
		
		connected = true;
		
		msgRecei = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		msgSend = new PrintWriter(socket.getOutputStream());
	}
	
	public void init() throws IOException, SecurityException {
		msgSend.println(key!=null);
		msgSend.flush();
		
		serverUseKey = Boolean.valueOf(msgRecei.readLine());
	}
	
	public void sendPackage(Package pack) {
		send(pack);
	}
	
	//public synchronized void sendArray(PackageType contentArray, String[] array) {sendArray(contentArray.getId(), array);}
	
	/*public synchronized void sendArray(int contentArray, String[] array) {
		PackageBuilder bui = new PackageBuilder(PackageType.ARRAY.getId());
		PackageObject objArray = new PackageObject("value");
		
		objArray.addItem("type", String.valueOf(contentArray));
		objArray.addItemArray("array", array);
		//System.out.println(bui.build().getFile());
	}*/
	
	
	public synchronized void send(Object obj) {
		msgSend.println(serverUseKey&&key!=null?key.crypt(obj.toString()):obj.toString());
		msgSend.flush();
	}
	
	public void close(String msg) throws IOException {
		msgSend.close();
		msgRecei.close();
		socket.close();
		connected = false;
		
		Debug.log("You are disconnected: " + msg);
	}
	
	public synchronized void sendFile(File file, String[] clientReceivers) throws SecurityException, IOException {
		PackageBuilder bui = new PackageBuilder(PackageType.FILE.getId());
		PackageObject objFile = new PackageObject("value");
		
		String path = file.getPath();
		String fileName = path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf('.'));
		String extention = path.substring(path.lastIndexOf('.') + 1);
		
		byte[] data = new byte[(int) file.length()];
		
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		
		in.readFully(data);
		in.close();
		
		objFile.addItem("fileName", fileName);
		objFile.addItem("extention", extention);
		objFile.addItemArray("data", data);
		
		bui.addItemArray("clientReceivers", clientReceivers);
		
		bui.addValue(objFile);
		//length  77
		
		
		//System.out.println(bui.build().getFile() + "  tezs");
		//System.out.println(objFile.getArrayValue("data").length + "  tezs");
		
		new Thread(new Runnable() {
			public void run() {
				sendPackage(new Package(bui));

			}
		}).start();
		
	}
	
	public Package nextPackage() throws SecurityException {
		String n = nextString();
		//System.out.println(n);
		return PackagePareser.parse(n);
	}
	
	private String nextString() throws SecurityException {
		String msg = null;
		
		try{msg = msgRecei.readLine();}
		catch(IOException e) {return null;}
		
		return serverUseKey&&key!=null?!msg.equals("")?key.decrypt(msg):msg:msg;
	}
	
	public String getPseudo() {return pseudo;}
	public String getPassword() {return password;}
	
	public boolean isUseKey() {return key!=null;}
	public boolean isServerUseKey() {return serverUseKey;}
	public boolean isConnected() {return connected;}
	
	public SocketClient setKey(Key key) {this.key = key; return this;}
	public SocketClient setPseudo(String pseudo) {this.pseudo = pseudo; return this;}
	public SocketClient setPassword(String password) {this.password = password; return this;}

}
