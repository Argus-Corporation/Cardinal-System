package net.argus.gui.dialog;

import net.argus.gui.frame.Frame;

public class Dialog extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6557708944979560107L;
	
	public Dialog() {
		super();
		init();
	}
	
	public Dialog(String title) {
		super(title);
		init();
	}
	
	private void init() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	
}
