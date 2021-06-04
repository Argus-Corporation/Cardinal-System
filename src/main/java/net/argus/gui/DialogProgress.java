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
	
	public void showDialog(int min, int max, String statusText) {
		setStatusText(statusText);
		createPanel(min, max);
		showDialog();
	}
	
	public void showDialog() {
		this.setSize(350, 100);
		this.setLocationRelativeTo(parent);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		
		this.setVisible(true);
	}
	
	public void updateStatus() {
		status.setText(progress.getValue() + " " + statusText + " " + max);
	}
	
	private void createPanel(int min, int max) {
		JPanel pan = new JPanel();
		this.setContentPane(pan);
		
		this.min = min;
		this.max = max;
		
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
			public void windowClosing(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});
		
	}
	
	public void setStatusText(String statusText) {this.statusText = statusText;}
	
	public void setProgress(int min, int max) {
		createPanel(min, max);
		updateStatus();
	}
	
	public void setValue(int n) {progress.setValue(n); updateStatus();}
	
	public int getValue() {return progress.getValue();}
	
	public void exit() {
		this.setVisible(false);
	}
	
	public static void main(String[] args) throws InterruptedException {
		DialogProgress dp = new DialogProgress("erts", null);
		dp.setStatusText("fichier native telecharger sur");
		dp.setProgress(0, 5);
		dp.showDialog();
		for(int i = 0; i < 11; i++) {
			dp.setValue(i);
			Thread.sleep(250);
		}
		dp.setStatusText("fichier native telecharger sur");
		dp.setProgress(0, 5);
		for(int i = 0; i < 6; i++) {
			dp.setValue(i);
			Thread.sleep(250);
		}
	}

}
