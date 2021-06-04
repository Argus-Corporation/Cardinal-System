package net.argus.util;

import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import javax.swing.ImageIcon;

import net.argus.file.FileManager;
import net.argus.system.InitializationSystem;
import net.argus.system.OS;
import net.argus.system.UserSystem;

public class Notification {
	
	private static boolean isFirst = true;
	private static SystemTray tray;
	private static TrayIcon trayIcon;
	
	/**
	 * Cette methode affiche une notification
	 * @param mes
	 * @param name
	 * @param projectName
	 * @param type
	 * @param iconPath
	 */
	public static void showNotification(String mes, String name, String projectName, MessageType type, String iconPath) {
		if(OS.currentOS() != OS.LINUX)
			try {
			    if(isFirst) {
			    	tray = SystemTray.getSystemTray();
			    	trayIcon = new TrayIcon(new ImageIcon(iconPath).getImage(), projectName);
			    	tray.add(trayIcon);
			    	isFirst = false;
			    } 
			    trayIcon.displayMessage(mes, name, type);
			}catch(Exception e) {e.printStackTrace();}
	}
	
	/**
	 * Cette methode affiche une notification
	 * @param mes
	 * @param name
	 * @param projectName
	 * @param type
	 * @param icon
	 */
	public static void showNotification(String mes, String name, String projectName, MessageType type, Image icon) {
		try {
		    if(isFirst) {
		    	tray = SystemTray.getSystemTray();
		    	trayIcon = new TrayIcon(icon, projectName);
		    	tray.add(trayIcon);
		    	isFirst = false;
		    } 
		    trayIcon.displayMessage(mes, name, type);
		}catch(Exception e) {e.printStackTrace();}
	}
	
	public static void main(String[] args) {
		InitializationSystem.initSystem(args, UserSystem.getDefaultInitializedSystemManager());

		Notification.showNotification("test", " ", "Argus", MessageType.ERROR, FileManager.getPath("res/favIcon16x16.png"));
		
	}
	
}
