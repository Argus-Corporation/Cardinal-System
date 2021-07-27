package net.argus.gui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class OptionPane {
	
	public static int showDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
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
	
	@SuppressWarnings("deprecation")
	public static String showPasswordDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
		Panel pan = new Panel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		
		PasswordField pass = new PasswordField();
		
		pan.add(new Label(message.toString(), false));
		pan.add(pass);
		
		showDialog(parentComponent, pan, title, optionType, messageType);
		
		return pass.getText();
	}
	
	public static int showErrorDialog(Component parentComponent, String programName, Throwable e) {
		return showDialog(parentComponent,
				"<html>An error occurred in " + programName + "<hr/>" +
 				e.getClass().getCanonicalName() + ": " + e.getMessage()  + "</html>", "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void main(String[] args) {
		showPasswordDialog(null, null, null, 0, 0);
	}
	
}
