package net.argus.gui;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

public class ComboBox<T> extends JComboBox<T> implements Element {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2532500270467799679L;
	
	private static final boolean isBack = true;
	private static final boolean isFore = true;
	private static final boolean isFont = true;
	
	private int oldSelectedIndex = -1;
	
	public ComboBox(T[] data) {
		super(data);
		
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
	}
	
	public ComboBox(ComboBoxModel<T> model) {
		super(model);
		
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
	}
	
	@Override
	public void setSelectedIndex(int anIndex) {
		if(getSelectedIndex() != anIndex)
			oldSelectedIndex = getSelectedIndex();
		
		super.setSelectedIndex(anIndex);
	}
	
	public int getOldSelectedIndex() {
		return oldSelectedIndex;
	}

}
