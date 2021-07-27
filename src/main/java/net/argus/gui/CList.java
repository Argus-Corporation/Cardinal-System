package net.argus.gui;

import javax.swing.JList;
import javax.swing.ListModel;

public class CList<E> extends JList<E> implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5382649657261072379L;
	
	public CList() {
		super();
		common();
	}
	
	public CList(E[] array) {
		super(array);
		common();
	}
	
	public CList(ListModel<E> model) {
		super(model);
		common();
	}
	
	private void common() {
		FontRegister.addElement(this);
		ForegroundRegiter.addElement(this);
		BackgoundRegister.addElement(this);
	}

	@Override
	public void setText() {}

	@Override
	public String getElementName() {
		return "List";
	}

}
