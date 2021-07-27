package net.argus.gui;

import javax.swing.JPasswordField;

public class PasswordField extends JPasswordField implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6445008515087985643L;
	
	public PasswordField() {
		super();
		common();
	}
	
	public PasswordField(int size) {
		super(size);
		common();
	}
	
	public PasswordField(String text) {
		super(text);
		common();
	}
	
	private void common() {
		BackgoundRegister.addElement(this);
		FontRegister.addElement(this);
		ForegroundRegiter.addElement(this);
		
	}

	@Override
	public void setText() {}

	@Override
	public String getElementName() {
		return "PasswordField";
	}

}
