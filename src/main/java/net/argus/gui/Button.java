package net.argus.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import net.argus.lang.Lang;
import net.argus.lang.LangRegister;

public class Button extends JButton implements Element, GUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3879913755145121820L;
	
	private String name, id;
	private static final boolean isBack = true;
	private static final boolean isFore = true;
	private static final boolean isFont = true;
	
	public Button(String name) {
		this(name, true);
	}
	
	public Button(String name, String id) {
		this(name, id, true);
	}
	
	public Button(String name, boolean lang) {
		this(name, null, lang);
	}
	
	public Button(String name, String id, boolean lang) {
		super();
		LangRegister.addElementLanguage(this);
		FontRegister.addElement(this);
		BackgoundRegister.addElement(this);
		ForegroundRegiter.addElement(this);
		
		this.name = name;
		this.id = id;
		if(lang)
			this.setText();
		else
			this.setText(name);
	}
	
	public Button(ImageIcon icon) {
		super(icon);
		nameTypes.add(BUTTON);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
		this.setFocusPainted(false);
	}
	
	public String getSourceText() {return name;}
	public String getId() {return id;}
	
	@Override
	public void setText() {this.setText(Lang.get(BUTTON + "." + name + ".name"));}

	@Override
	public String getElementName() {
		return "Button";
	}

}
