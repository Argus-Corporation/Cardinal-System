package net.argus.gui;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class OptionPane {
	
	public static int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
		JOptionPane pane = new JOptionPane(message, messageType, optionType);
		
		JDialog dialog = pane.createDialog(parentComponent, title);
		dialog.setAlwaysOnTop(true);
		
		dialog.setVisible(true);
		dialog.dispose();
		
		Object selectedValue = pane.getValue();
		if(selectedValue == null)
			return JOptionPane.CLOSED_OPTION;
		
		if(selectedValue instanceof Integer)
			return ((Integer) selectedValue).intValue();
		return JOptionPane.CLOSED_OPTION;
	}
	
	public static int showMessageDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
		return showConfirmDialog(parentComponent, message, title, optionType, messageType);
	}
	
	public static int showErrorDialog(Component parentComponent, String programName, Throwable e) {
		return showConfirmDialog(parentComponent,
				"<html>An error occurred in " + programName + "<hr/>" +
 				e.getClass().getCanonicalName() + ": " + e.getMessage()  + "</html>", "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
	}
	
}
