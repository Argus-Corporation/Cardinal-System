package net.argus.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.argus.client.Client;
import net.argus.client.ClientManager;
import net.argus.client.ProcessClient;
import net.argus.client.ProcessListener;
import net.argus.exception.SecurityException;
import net.argus.file.FileManager;
import net.argus.file.Properties;
import net.argus.file.cjson.CJSONObject;
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
import net.argus.util.Notification;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageType;

public class ExampleClient {
	
	private Frame fen;
	private Splash spl;
	private JEditorPane allMsg;
	private Button send;
	
	private Client client;
	
	public ExampleClient() throws UnknownHostException, IOException {
		Properties config = new Properties("client_config", "bin");
		spl = new Splash("ExampleClient", Icon.getIcon(FileManager.getPath("res/logo.png"), Display.getWidhtDisplay() - 50), 1000);
		spl.play();
		
		CSSEngine.run("client", "bin/css");
		
		fen = new Frame("[ARGUS] Client", FileManager.getPath("res/favIcon32x32.png"), new boolean[] {true, true, true}, config);
		fen.setLocationRelativeTo(null);
		fen.setIcon(FileManager.getPath("res/favIcon16x16.png"));
		
		fen.add(BorderLayout.CENTER, getChatPanel());
		
		while(!spl.isValid())
			fen.setVisible(false);
			
		fen.setVisible(true);
		connect();
	}
	
	public Panel getChatPanel() throws UnknownHostException, IOException {
		Panel main = new Panel();
		main.setLayout(new BorderLayout());
		
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
				if(client != null && client.isConnected()) {
						client.sendPackage(new Package(new PackageBuilder(PackageType.LOG_OUT.getId()).addValue("message", "Frame Closing")));
					
					client.getProcessClient().stop();
				}
				new FrameAnimation(fen).play();
				UserSystem.exit(0);
				
			}
		});
		
		send.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {
			if(msg.getText().toCharArray()[0] == '/')
				client.sendPackage(new Package(new PackageBuilder(PackageType.COMMANDE.getId()).addValue("command", msg.getText())));
			else
				client.sendPackage(new Package(new PackageBuilder(PackageType.MESSAGE.getId()).addValue("message", msg.getText())));
			
			setMessage(new String[] {"Vous", msg.getText()});
			msg.copyData();
			msg.setText("");
			
		}});
		
		msg.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {
			send.getActionListeners()[0].actionPerformed(arg0);  
			
			/*try {client.sendFile(new File("D:\\Django\\document 1\\Git\\config.cjson"), new String[] {"lol"});}
			catch(SecurityException | IOException e) {e.printStackTrace();}*/
			
		}});	
		
		south.add(msg);
		south.add(send);
		main.add(BorderLayout.CENTER, sp);
		main.add(BorderLayout.SOUTH, south);
		
		return main;
	}
	

	public void connect() {
		List<String> hostName = getHostName();
		
		JDialog dial = new JDialog();
		dial.setSize(300, 150);
		dial.setLocationRelativeTo(fen);
		dial.setAlwaysOnTop(true);
		dial.setResizable(false);
		dial.setIconImage(Icon.getIcon(FileManager.getPath("res/favIcon16x16.png")).getImage());
		dial.setLayout(new BorderLayout());
		
		Panel north = new Panel();
		Panel south = new Panel();
		Panel option = new Panel();
		
		JCheckBox saveCheck = new JCheckBox("IP register");
		north.add(saveCheck);
		
		JCheckBox newCheck = new JCheckBox("new IP");
		south.add(newCheck);
		
		JComboBox<String> list = new JComboBox<String>((String[]) hostName.toArray(new String[hostName.size()]));
		north.add(list);
		
		JTextField host = new JTextField(12);
		south.add(host);
		
		Button ok = new Button("   OK   ", false);
		option.add(ok);
		
		Button cancel = new Button("Cancel", false);
		option.add(cancel);
		
		saveCheck.setSelected(true);
		host.setEnabled(false);
		
		saveCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCheck.setSelected(true);
				newCheck.setSelected(false);
				
				list.setEnabled(true);
				host.setEnabled(false);
			}
		});
		
		newCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newCheck.setSelected(true);
				saveCheck.setSelected(false);
				
				list.setEnabled(false);
				host.setEnabled(true);
			}
		});
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saveCheck.isSelected()) {
					Properties ipConfig = new Properties("ip", "/");
					
					try {
						dial.setVisible(false);
						setInfo(ipConfig.getString("ip" + hostName.indexOf(list.getSelectedItem())));
					}catch(IOException e1) {
						e1.printStackTrace();
					}
				}else {
					try {
						dial.setVisible(false);
						setInfo(host.getText());
					}catch(IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dial.setVisible(false);
			}
		});
		
		dial.add(BorderLayout.NORTH, north);
		dial.add(BorderLayout.CENTER, south);
		dial.add(BorderLayout.SOUTH, option);
		
		dial.pack();
		
		dial.setVisible(true);
	}
	
	public List<String> getHostName() {
		Properties ip = new Properties("ip", "/");
		
		List<String> name = new ArrayList<String>();
		
		for(int i = 0; i < ip.getNumberLine() / 2; i++)
			name.add(ip.getString("ip" + i + ".name"));
		return name;
	}
	
	public void setInfo(String host) throws UnknownHostException, IOException {
		int port = 11066;
		
		String pseudo = JOptionPane.showInputDialog(fen, "Pseudo", "Client", JOptionPane.DEFAULT_OPTION);
		String password = JOptionPane.showInputDialog(fen, "Password", "Client", JOptionPane.DEFAULT_OPTION);
		
		client = new Client(host, port);
		
		client.setPassword(password);
		client.setPseudo(pseudo);
		
		client.addClientManager(new ClientManager() {
			public void receivePackage(Package pack, ProcessClient thisObj) throws SecurityException {
				int msgId = pack.getType();
				
				switch(msgId) {
					case ProcessClient.ARRAY:
						int arrayType = Integer.valueOf(pack.getObject("value").getValue("type").toString());
						
						switch(arrayType) {
							case ProcessClient.PSEUDO:
								CJSONObject[] array = pack.getObject("value").getArrayValue("array");
								
								for(int i = 0; i < array.length; i++)
									setMessage(new String[] {"", array[i].toString()});
									
								break;
						}
						break;
				}
				
			}
			
			public void disconnected(String msg) throws SecurityException {
				String[] value = new String[2];
				value[0] = "SYSTEM";
				value[1] = msg;
				client.getProcessListener().addSystemMessage(value);
			}
		});
		
		client.addProcessListener(new ProcessListener() {
			public void addSystemMessage(String[] value) {
				addMessage(value);
				if(!fen.isActive())
					Notification.showNotification("Vous avez un nouveau message de " + value[0], value[1], "Argus", MessageType.NONE, FileManager.getPath("res/favIcon16x16.png"));
			}
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
		
		Debug.addBlackList(ThreadManager.UPDATE_UI);
		
		Look.chageLook(UIManager.getSystemLookAndFeelClassName());
		new ExampleClient();
	}

}
