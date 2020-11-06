package net.argus.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ScrollPane extends JScrollPane implements Element {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7208646137061648299L;
	
	public ScrollPane(SListe<?> data) {
		super(data);
	}
	
	public ScrollPane(JPanel pan) {
		super(pan);
	}

}
