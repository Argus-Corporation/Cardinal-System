package net.argus.gui;

import javax.swing.JTextArea;
import javax.swing.text.Document;

public class TextArea extends JTextArea implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4261622498028565155L;
	
	public TextArea() {
		super();
		init();
	}

	public TextArea(String text) {
		super(text);
		init();
	}

	public TextArea(int rows, int columns) {
		super(rows, columns);
		init();
	}

	public TextArea(String text, int rows, int columns) {
		super(text, rows, columns);
		init();
	}

	public TextArea(Document doc) {
		super(doc);
		init();
	}
		
	public TextArea(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
		init();
	}
	
	private void init() {
		FontRegister.addElement(this);
		BackgoundRegister.addElement(this);
		ForegroundRegiter.addElement(this);
	}

	@Override
	public void setText() {}

	@Override
	public String getElementName() {
		return "TextArea";
	}

}
