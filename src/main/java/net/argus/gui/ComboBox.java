package net.argus.gui;

import javax.swing.JComboBox;

public class ComboBox<T> extends JComboBox<T> implements Element {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2532500270467799679L;
	
	@SuppressWarnings("unused")
	private String name;
	private static final String nameType = "combobox";
	private static final boolean isBack = true;
	private static final boolean isFore = true;
	private static final boolean isFont = true;
	
	public ComboBox(T[] data, String name) {
		super(data);
		
		this.name = nameType + "." + name + ".name";
		nameTypes.add(nameType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
		
	}

}
