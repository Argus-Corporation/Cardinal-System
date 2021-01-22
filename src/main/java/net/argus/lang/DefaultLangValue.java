package net.argus.lang;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

public class DefaultLangValue {
	
	public static final DefaultLangValue fr_FR = new DefaultLangValue()
			.add("OptionPane.cancelButtonText", "Annuler")
			.add("OptionPane.noButtonText", "Non")
			.add("OptionPane.yesButtonText", "Oui");
	
	public static final DefaultLangValue en_US = new DefaultLangValue()
			.add("OptionPane.cancelButtonText", "Cancel")
			.add("OptionPane.noButtonText", "No")
			.add("OptionPane.yesButtonText", "Yes");
	
	private List<String> elementName = new ArrayList<String>(); 
	private List<String> elementValue = new ArrayList<String>(); 
	
	public DefaultLangValue add(String name, String value) {
		elementName.add(name);
		elementValue.add(value);
		return this;
	}
	
	public void apply() {
		for(int i = 0; i < elementName.size(); i++)
			UIManager.put(elementName.get(i), elementValue.get(i));
	}
}
