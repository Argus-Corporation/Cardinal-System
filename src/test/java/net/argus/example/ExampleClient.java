package net.argus.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import net.argus.client.Client;
import net.argus.client.ClientManager;
import net.argus.client.ProcessClient;
import net.argus.client.ProcessListener;
import net.argus.exception.SecurityException;
import net.argus.file.FileManager;
import net.argus.file.Properties;
import net.argus.file.css.CSSEngine;
import net.argus.gui.Button;
import net.argus.gui.Frame;
import net.argus.gui.FrameListener;
import net.argus.gui.Icon;
import net.argus.gui.Look;
import net.argus.gui.Panel;
import net.argus.gui.Splash;
import net.argus.gui.TextField;
import net.argus.gui.animation.FrameAnimation;
import net.argus.system.InitializedSystem;
import net.argus.system.UserSystem;
import net.argus.util.Display;
import net.argus.util.Package;
import net.argus.util.PackageType;

public class ExampleClient {
	
	private Frame fen;
	private JEditorPane allMsg;
	private Button send;
	
	private Client client;
	
	public ExampleClient() throws UnknownHostException, IOException {
		Properties config = new Properties("client_config", "bin");
		Splash spl = new Splash("ExampleClient", Icon.getIcon(FileManager.getPath("res/logo.png"), Display.getWidhtDisplay() - 50), 2000);
		spl.play();
		
		CSSEngine.run("client", "bin/css");
		
		fen = new Frame("[ARGUS] Client", FileManager.getPath("res/favIcon32x32.png"), new boolean[] {true, true, true}, config);
		fen.setLocationRelativeTo(null);
		fen.setIcon(FileManager.getPath("res/favIcon16x16.png"));
		
		Panel south = new Panel();
		allMsg = new JEditorPane();
		JScrollPane sp = new JScrollPane(allMsg);
		
		allMsg.setEditable(false);
		
		TextField msg = new TextField(0, true);
		send = new Button("send");
		
		msg.setPreferredSize(new Dimension(fen.getSize().width - 200, 25));
		
		fen.addFrameListener(new FrameListener() {
			public void frameResizing() {
				msg.setPreferredSize(new Dimension(fen.getSize().width - 200, 25));
			}
			public void frameMinimalized() {}
			@SuppressWarnings("deprecation")
			public void frameClosing() {
				if(client.isConnected()) {
						client.sendPackage(new Package(PackageType.LOG_OUT, "Frame Closing"));
					
					client.getProcessClient().stop();
				}
				new FrameAnimation(fen).play();
				UserSystem.exit(0);
				
			}
		});
		
		send.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {
			client.sendPackage(new Package(PackageType.MESSAGE, msg.getText()));
			setMessage(new String[] {"Vous", msg.getText()});
			msg.copyData();
			msg.setText("");
			
		}});
		
		msg.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {
			send.getActionListeners()[0].actionPerformed(arg0);
		}});	
		
		south.add(msg);
		south.add(send);
		fen.add(BorderLayout.CENTER, sp);
		fen.add(BorderLayout.SOUTH, south);
		
		while(!spl.isValid()) {
			fen.setVisible(false);
		}
		
		fen.setVisible(true);
		setClient();
	}
	
	public void setClient() throws UnknownHostException, IOException {
		int port = 11066;
		String host = JOptionPane.showInputDialog(fen, "Host", "Client", JOptionPane.DEFAULT_OPTION);
		
		String pseudo = JOptionPane.showInputDialog(fen, "Pseudo", "Client", JOptionPane.DEFAULT_OPTION);
		String password = JOptionPane.showInputDialog(fen, "Password", "Client", JOptionPane.DEFAULT_OPTION);
		
		client = new Client(host, port, 0x11066);
		
		client.setPassword(password);
		client.setPseudo(pseudo);
		
		client.addClientManager(new ClientManager() {
			public void receiveMessage(int msgType) throws SecurityException {
				switch(msgType) {
					case ProcessClient.ARRAY:
						int arrayType = client.getSocketClient().receivePackage().getPackageType().getId();
						
						switch(arrayType) {
							case ProcessClient.PSEUDO:
								String[] pseudos = client.getSocketClient().receiveArray();
								for(int i = 0; i < pseudos.length; i++) {
									setMessage(new String[] {"", pseudos[i]});
								}
								break;
						}
						break;
				}
				
			}
			
			public void disconnected(String msg) throws SecurityException {
				String[] value = new String[2];
				value[0] = "SYSTEM";
				value[1] = msg;
				setMessage(value);
			}
		});
		
		client.addProcessListener(new ProcessListener() {
			public void addSystemMessage(String[] value) {addMessage(value);}
			public void addMessage(String[] value) {setMessage(value);}
		});
		
		try {client.start();
		}catch(IOException e) {
			String errmsg = "";
			if(e instanceof SocketException) errmsg = "Time Out";
			else if(e instanceof UnknownHostException) errmsg = "Unknown Host";
			JOptionPane.showMessageDialog(fen, errmsg, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setMessage(String[] value) {
		allMsg.setText(allMsg.getText() + value[0] + ": " + value[1] + "\n");
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		InitializedSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());
		Thread.currentThread().setName("client");
		
		Look.chageLook(UIManager.getSystemLookAndFeelClassName());
		new ExampleClient();
		
	}

}
