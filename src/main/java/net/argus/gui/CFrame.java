package net.argus.gui;

import javax.swing.JFrame;

public class CFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -805549194374051037L;
	
	private String tempTitle = null;
	
	public CFrame() {
		super();
	}
	
	public CFrame(String title) {
		super(title);
		tempTitle = title;
	}
	
	@Override
	public void setTitle(String title) {
		tempTitle = title;
		super.setTitle(title);
	}
	
	public void setFullscreenable(boolean fullscreenable) {
		getRootPane().putClientProperty("apple.awt.fullscreenable", fullscreenable);
	}
	
	public void setTitleBarTransparent(boolean transparent) {	
		getRootPane().putClientProperty("apple.awt.transparentTitleBar", transparent);
	}
	
	public void setFullWindowContent(boolean fullContent) {
	    getRootPane().putClientProperty("apple.awt.fullWindowContent", fullContent);
	}
	
	public void setVisibleTitle(boolean visible) {
		//getRootPane().putClientProperty("apple.awt.windowTitleVisible", visible);
		if(visible)
			super.setTitle(tempTitle);
		else
			super.setTitle(null);
	}
	
	public String getRealTitle() {
		return tempTitle;
	}

}
