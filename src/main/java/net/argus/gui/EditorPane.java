package net.argus.gui;

import javax.swing.JEditorPane;

public class EditorPane extends JEditorPane implements GUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5651193330298224614L;
	
	public EditorPane() {
		FontRegister.addElement(this);
		BackgoundRegister.addElement(this);
		ForegroundRegiter.addElement(this);
	}

	@Override
	public void setText() {}

	@Override
	public String getElementName() {
		return "EditorPane";
	}

}
