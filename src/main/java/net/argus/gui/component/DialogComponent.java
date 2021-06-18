package net.argus.gui.component;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import net.argus.gui.Panel;

public abstract class DialogComponent {
	
	private JDialog dialog = new JDialog();
	private Component parent;
	
	private boolean pack, cloes = true;
	
	public DialogComponent(Component parnet) {
		this.parent = parnet;
		
		dialog.addWindowListener(getWindowListener());
	}
	
	/**
	 * setVisible
	 * @param v
	 */
	public void setVisible(boolean v) {
		if(v) show();
		else hide();
	}
	
	/**
	 * setAlwaysOnTop
	 * @param a
	 */
	public void setAlwaysOnTop(boolean a) {
		dialog.setAlwaysOnTop(a);
	}
	
	/**
	 * setResizable
	 * @param r
	 */
	public void setResizable(boolean r) {
		dialog.setResizable(r);
	}
	
	/**
	 * setSize
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		dialog.setSize(width, height);
	}
	
	/**
	 * setIcon
	 * @param icon
	 */
	public void setIcon(Image icon) {
		dialog.setIconImage(icon);
	}
	
	/**
	 * setPack
	 * @param pack
	 */
	public void setPack(boolean pack) {
		this.pack = pack;
	}
	
	/**
	 * setClose
	 * @param c
	 */
	public void setClose(boolean c) {
		this.cloes = c;
	}
	
	/**
	 * setTitle
	 * @param title
	 */
	public void setTitle(String title) {
		dialog.setTitle(title);
	}
	
	/**
	 * addWindowListener
	 * @param l
	 */
	public void addWindowListener(WindowListener l) {
		dialog.addWindowListener(l);
	}
	
	/**
	 * addFocusListener
	 * @param l
	 */
	public void addFocusListener(FocusListener l) {
		dialog.addFocusListener(l);
	}
	
	/**
	 * isVisible
	 * @return
	 */
	public boolean isVisible() {
		return dialog.isVisible();
	}
	
	/**
	 * load
	 */
	protected void load() {
		dialog.setContentPane(getComponent());
		dialog.setLocationRelativeTo(parent);
		
		if(pack) dialog.pack();
	}
	
	/**
	 * show
	 */
	@SuppressWarnings("deprecation")
	public void show() {
		load();
		dialog.show();
	}
	
	/**
	 * hide
	 */
	@SuppressWarnings("deprecation")
	public void hide() {
		dialog.hide();
	}
	
	/**
	 * getDialog
	 * @return
	 */
	public JDialog getDialog() {
		return dialog;
	}
	
	/**
	 * getWindowListener
	 * @return
	 */
	private WindowListener getWindowListener() {
		return new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			@SuppressWarnings("deprecation") public void windowClosing(WindowEvent e) {if(cloes) hide(); else Thread.currentThread().stop();}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			
		};
	}
	
	/**
	 * getComponent
	 * @return
	 */
	public abstract Panel getComponent();
	
}
