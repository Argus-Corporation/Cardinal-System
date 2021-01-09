package net.argus.example.client.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;

import net.argus.file.Properties;
import net.argus.gui.Frame;
import net.argus.gui.FrameListener;

public class GUIClient {
	
	private static Properties config;
	
	public static ClientFrame frame;
	
	public static MenuBarClient menuBar = new MenuBarClient();
	public static PanelChatClient panChat = new PanelChatClient();
	
	static {
		config = new Properties("config", "bin");
		
		
		frame = new ClientFrame(config);
		
		try {
			frame.add(BorderLayout.NORTH, menuBar.getMenuBar());
			frame.add(BorderLayout.CENTER, panChat.getChatPanel());
		}catch(IOException e) {}
		
	}
	
	public static void connect() {
		menuBar.getFast().setEnabled(false);
		menuBar.getJoin().setEnabled(false);
		menuBar.getLeave().setEnabled(true);
	}
	
	public static void leave() {
		menuBar.getFast().setEnabled(true);
		menuBar.getJoin().setEnabled(true);
		menuBar.getLeave().setEnabled(false);
	}
	
	public static Frame getFrame() {return frame;}
	public static Properties getProperties() {return config;}
	
	public static void addFrameListener(FrameListener listener) {frame.addFrameListener(listener);}
	
	public static void addFastAction(ActionListener actionListener) {menuBar.getFast().addActionListener(actionListener);}
	public static void addJoinAction(ActionListener actionListener) {menuBar.getJoin().addActionListener(actionListener);}
	public static void addLeaveAction(ActionListener actionListener) {menuBar.getLeave().addActionListener(actionListener);}
	
	public static void addSendAction(ActionListener actionListener) {panChat.getSendButton().addActionListener(actionListener);}
	
	public static void addMessage(String[] value) {panChat.addMessage(value);}
	public static void setVisible(boolean v) {frame.setVisible(v);}
	
}
