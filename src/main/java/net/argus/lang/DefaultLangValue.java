package net.argus.lang;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

public class DefaultLangValue {
	
	public static final DefaultLangValue fr_FR = new DefaultLangValue()
			.add("OptionPane.cancelButtonText", "Annuler")
			.add("OptionPane.noButtonText", "Non")
			.add("OptionPane.yesButtonText", "Oui")
			
			.add("Frame.titleErrorText", "Erreur")
			
			.add("Text.status", "Statut");
	
	public static final DefaultLangValue en_US = new DefaultLangValue()
			.add("OptionPane.cancelButtonText", "Cancel")
			.add("OptionPane.noButtonText", "No")
			.add("OptionPane.yesButtonText", "Yes")
			
			.add("Frame.titleErrorText", "Error")
			
			.add("Text.status", "Statuts");
	
	public static final DefaultLangValue ja_JP = new DefaultLangValue()
			.add("OptionPane.cancelButtonText", "キャンセル")
			.add("OptionPane.noButtonText", "番号")
			.add("OptionPane.yesButtonText", "はい")
			
			.add("Frame.titleErrorText", "エラー")
			
			.add("Text.status", "状態");
	
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
