package net.argus.gui.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.FocusListener;
import java.awt.event.WindowListener;

import net.argus.event.gui.frame.FrameListener;
import net.argus.gui.GUI;
import net.argus.gui.Panel;
import net.argus.gui.dialog.Dialog;
import net.argus.lang.Lang;
import net.argus.lang.LangRegister;

public abstract class DialogComponent implements GUI {
	
	private Dialog dialog = new Dialog();
	
	private String title;
		
	private boolean pack, titleLang;
	
	public DialogComponent() {
		
		LangRegister.addElementLanguage(this);
		
		load();
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
	 * setIcon
	 * @param icon
	 */
	public void setDialogIcon(Image icon) {
		dialog.setFrameIconImage(icon);
	}
	
	/**
	 * setPack
	 * @param pack
	 */
	public void setPack(boolean pack) {
		this.pack = pack;
	}
	
	/**
	 * setTitleLang
	 * @param tl
	 */
	public void setTitleLang(boolean tl) {
		this.titleLang = tl;
	}
	
	/**
	 * setLocationRelativeTo
	 * @param c
	 */
	public void setLocationRelativeTo(Component c) {
		dialog.setLocationRelativeTo(c);
	}
	
	/**
	 * setTitle
	 * @param title
	 */
	public void setTitle(String title) {
		if(titleLang) {
			this.title = title;
			dialog.setTitle(Lang.get(TITLE + "." + title + ".name"));
		}else
			dialog.setTitle(title);
			
	}
	
	/**
	 * addWindowListener
	 * @param l
	 */
	@Deprecated
	public void addWindowListener(WindowListener l) {
		dialog.addWindowListener(l);
	}
	
	/**
	 * addFrameListener
	 * @param listener
	 */
	public void addFrameListener(FrameListener listener) {
		dialog.addFrameListener(listener);
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
		
		if(pack) dialog.pack();
 	}
	
	/**
	 * show
	 */
	public void show() {
		dialog.setVisible(true);
	}
	
	/**
	 * hide
	 */
	public void hide() {
		dialog.setVisible(false);
	}
	
	/**
	 * getDialog
	 * @return
	 */
	public Dialog getDialog() {
		return dialog;
	}
	
	/**
	 * setFont
	 * @param font
	 */
	@Override
	public void setFont(Font font) {}
	
	/**
	 * setBackground
	 * @param bg
	 */
	@Override
	public void setBackground(Color bg) {}
	
	/**
	 * setForeground
	 * @param fore
	 */
	@Override
	public void setForeground(Color fore) {}
	
	/**
	 * getElementName
	 */
	@Override
	public String getElementName() {return "Dialog";}
	
	/**
	 * setText
	 */
	@Override
	public void setText() {
		if(titleLang && title != null)
			setTitle(title);
	}
	
	/**
	 * getComponent
	 * @return
	 */
	public abstract Panel getComponent();
	
}
