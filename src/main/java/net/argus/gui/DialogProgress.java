package net.argus.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class DialogProgress extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6656504574173625724L;
	
	private Component parent;
	
	private JProgressBar progress;
	private JLabel status;
	
	private String statusText; 
	
	@SuppressWarnings("unused")
	private int min, max;
	
	public DialogProgress(String title, Component parent) {
		super();
		this.setTitle(title);
		this.parent = parent;
	}
	
	public void show(int min, int max, String statusText, boolean freezParent) {
		this.statusText = statusText;
		this.min = min;
		this.max = max;
		
		this.setSize(350, 100);
		this.setLocationRelativeTo(parent);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		
		if(freezParent) parent.setEnabled(false);
		
		JPanel pan = new JPanel();
		this.setContentPane(pan);
		
		progress = new JProgressBar(min, max);
		progress.setPreferredSize(new Dimension(325, 25));
		pan.add(progress);
		
		status = new JLabel(progress.getValue() + " " + statusText + " " + max);
		pan.add(status);
		
		this.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {if(freezParent) parent.setEnabled(true);}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});
		
		this.setVisible(true);
	}
	
	private void updateStatus() {
		status.setText(progress.getValue() + " " + statusText + " " + max);
	}
	
	public void setValue(int n) {progress.setValue(n); updateStatus();}
	
	public int getValue() {return progress.getValue();}
	
	public void exit() {
		this.setVisible(false);
	}
	
	/*public static void main(String[] args) throws InterruptedException {
		DialogProgress dp = new DialogProgress("erts", null);
		dp.show(0, 10, "fichier telecharger sur", false);
		for(int i = 0; i < 11; i++) {
			dp.setValue(i);
			Thread.sleep(250);
		}
	}*/

}
