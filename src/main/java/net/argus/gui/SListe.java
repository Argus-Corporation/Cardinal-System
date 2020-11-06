package net.argus.gui;

import javax.swing.JList;

public class SListe<T> extends JList<T> implements Element {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 862477362992444444L;
	
	private static final String nameType = "liste";
	private static final boolean isBack = true;
	private static final boolean isFore = true;
	private static final boolean isFont = true;
	
	public SListe(T[] data) {
		super(data);
		
		nameTypes.add(nameType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
	}

}
