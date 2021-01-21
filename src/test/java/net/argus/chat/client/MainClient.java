package net.argus.chat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import net.argus.chat.client.gui.Connect;
import net.argus.chat.client.gui.GUIClient;
import net.argus.file.FileManager;
import net.argus.file.css.CSSEngine;
import net.argus.gui.FrameListener;
import net.argus.gui.TextField;
import net.argus.security.Key;
import net.argus.system.InitializedSplash;
import net.argus.system.InitializedSystem;
import net.argus.util.ArrayManager;
import net.argus.util.Display;
import net.argus.util.pack.Package;
import net.argus.util.pack.PackageBuilder;
import net.argus.util.pack.PackageType;

public class MainClient {
	
	private ExampleClient client;
	private MainClient thisObj;
	
	public static String icon16;
	public static String icon32;
	
	public MainClient() {
		icon16 = FileManager.getPath("res/favicon16x16.png");
		icon32 = FileManager.getPath("res/favicon32x32.png");
		
		thisObj = this;
		
		GUIClient.addFastAction(getFastActionListener());
		GUIClient.addJoinAction(getJoinActionListener());
		GUIClient.addLeaveAction(getLeaveActionListener());
		
		GUIClient.addPreferenceAction(getPreferenceActionListener());
		
		GUIClient.addSendAction(getSendActionListener());
		
		GUIClient.addFrameListener(getFrameListener());
		InitializedSplash.getSplash().exit();
		
		while(!InitializedSplash.getSplash().isFinnish())
			GUIClient.setVisible(false);
			
		GUIClient.setVisible(true);
		
	}
	
	public ActionListener getFastActionListener() {
		return (ActionEvent e) -> new Connect(thisObj, true).start();
	}
	
	public ActionListener getJoinActionListener() {
		return (ActionEvent e) -> new Connect(thisObj, false).start();
	}
	
	public ActionListener getLeaveActionListener() {
		return (ActionEvent e) -> client.logOut();
	}
	
	public ActionListener getPreferenceActionListener() {
		return (ActionEvent e) -> GUIClient.configFrame.show();
	}
	
	public ActionListener getSendActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TextField field = GUIClient.panChat.getTextField();
				String msg = field.getText();
				
				if(client != null && client.isConnected() && ArrayManager.isExist(msg.toCharArray(), 0)) {
					if(msg.toCharArray()[0] == '/')
						client.sendPackage(new Package(new PackageBuilder(PackageType.COMMANDE.getId()).addValue("command", msg)));
					else
						client.sendPackage(new Package(new PackageBuilder(PackageType.MESSAGE.getId()).addValue("message", msg)));
				
					GUIClient.addMessage(new String[] {"Vous", msg});
				
					field.copyData();
					field.setText("");
				}
			}
		};
	
	}
	
	public FrameListener getFrameListener() {
		return new FrameListener() {
			public void frameResizing() {}
			@Override
			@SuppressWarnings("deprecation")
			public void frameClosing() {
				if(client != null && client.isConnected()) {
					client.sendPackage(new Package(new PackageBuilder(PackageType.LOG_OUT.getId()).addValue("message", "Frame Closing")));
					
					client.getProcessClient().stop();
				}
			}	
			public void frameMinimalized() {}
		};
	}
	
	public void connect(String host, String pseudo, String password) throws UnknownHostException, IOException {
		if(GUIClient.menuBar.isEncrypt())
			client = new ExampleClient(host, ClientConfig.DEFAULT_CRYPT_PORT, pseudo, password, new Key(ClientConfig.getDefaultKey()));
		else
			client = new ExampleClient(host, ClientConfig.DEFAULT_PORT, pseudo, password);
		
		client.addClientManager(new ClientManagerChat(client));
		client.addProcessListener(new ProcessListenerChat());

		try {
			client.start();
			
			GUIClient.connect();
		}catch(IOException e) {
			GUIClient.leave();
			
			String errmsg = "";
			if(e instanceof SocketException) errmsg = "Time Out";
			else if(e instanceof UnknownHostException) errmsg = "Unknown Host";
			
			JOptionPane.showMessageDialog(GUIClient.getFrame(), errmsg, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String[] args) {
		InitializedSystem.initSystem(args, true, new InitializedSplash("res/logo.png", Display.getWidhtDisplay() - 50, 0));
		Thread.currentThread().setName("client");
		
		CSSEngine.run("client", "bin/css");
		
		new MainClient();
	}

}
