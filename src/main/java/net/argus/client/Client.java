package net.argus.client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JTextField;

import net.argus.security.Key;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.Package;
import net.argus.util.PackageType;

public class Client {
	
	private static int CLIENT_VERSION;
	
	private String host;
	private int port;
	
	private SocketClient client;
	private ProcessClient process;
	private static boolean running;
	
	public Client(String host, int port, int version, Key key) throws UnknownHostException, IOException {
		Thread.currentThread().setName("CLIENT");
		Client.CLIENT_VERSION = version;
		
		this.host = host;
		this.port = port;
		
		client = new SocketClient(host, port, key);
		process = new ProcessClient(client);
	}
	
	public Client(String host, int port, int version) throws UnknownHostException, IOException {
		Thread.currentThread().setName("CLIENT");
		Client.CLIENT_VERSION = version;
		
		this.host = host;
		this.port = port;
		
		client = new SocketClient(host, port);
		process = new ProcessClient(client);
	}
	
	public void start() throws UnknownHostException, IOException {
		client.connect();
		
		running = true;
		if(client.getPseudo()==null) client.setPseudo("Client");
		process.start();
	}
	
	public void stop() {
		running = false;
	}
	
	public Client addProcessListener(ProcessListener listener) {process.addProcessListener(listener); return this;}
	public Client addClientManager(ClientManager manager) {process.addClientManager(manager); return this;}
	public Client addKey(Key key) {client.setKey(key); return this;}
	
	public Client setPseudo(String pseudo) {client.setPseudo(pseudo); return this;}
	public Client setPassword(String password) {client.setPassword(password); return this;}
	public Client sendPackage(Package pack) {client.sendPackage(pack); return this;}
	
	public boolean isConnected() {return client.isConnected();}
	
	public String getHost() {return host;}
	public int getPort() {return port;}
	
	public String getPseudo() {return client.getPseudo();}
	public String getPassword() {return client.getPassword();}
	
	public ProcessClient getProcessClient() {return process;}
	public SocketClient getSocketClient() {return client;}
	
	public static boolean isRunning() {return running;}
	public static int getVersion() {return CLIENT_VERSION;}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		JFrame fen = new JFrame();
		JEditorPane ep = new JEditorPane();
		JTextField tf = new JTextField();
		JButton send = new JButton("send");
		fen.setSize(500, 200);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.setLayout(new BorderLayout());
		fen.setLocationRelativeTo(null);
		
		fen.add(BorderLayout.CENTER, ep);
		fen.add(BorderLayout.SOUTH, tf);
		fen.add(BorderLayout.EAST, send);
		
		
		
		fen.setVisible(true);
		
		
		ProcessListener listener = new ProcessListener() {
			public void addMessage(String[] value) {
				ep.setText(ep.getText() + "\n" + value[0] + ": " + value[1]);
			}
			public void addSystemMessage(String[] value) {
				ep.setText(ep.getText() + "\n" + value[0] + ": " + value[1]);
			}
		};
		
		ClientManager manager = new ClientManager() {
			public void receiveMessage(int msgType) {
				//Debug.log("ID: " + msgType);
				
			}
			public void disconnected(String msg) {
				
			}
		};
		//Key key = new Key("$^ù$$;mm^$^dmsf$^sdµdPµ^mm$µMPµ;p:,$^;m:$^,;:877687^$ù*%µMPµ%m");
		
		//Client client = new Client("176.163.39.11", 11066, 0x11066, key);
		Client client = new Client("0.0.0.0", 11066, 0x11066);
		
		client.addProcessListener(listener);
		client.addClientManager(manager);
		client.start();
		
		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.sendPackage(new Package(PackageType.MESSAGE, tf.getText()));
				tf.setText("");
				
			}
		});
		
		fen.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				client.sendPackage(new Package(PackageType.LOG_OUT, "Frame Closing"));
				try {client.getSocketClient().close("Frame Closing");}
				catch(IOException e1) {e1.printStackTrace();}
			}
		});
	}

}
