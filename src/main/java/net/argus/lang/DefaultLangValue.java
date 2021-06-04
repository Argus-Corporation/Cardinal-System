package net.argus.lang;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import net.argus.util.DoubleStock;

public class DefaultLangValue {
	
	public static final DefaultLangValue fr_FR = new DefaultLangValue()
			.add("OptionPane.cancelButtonText", "Annuler")
			.add("OptionPane.noButtonText", "Non")
			.add("OptionPane.yesButtonText", "Oui")
			
			.add("Frame.titleErrorText", "Erreur")
			
			.add("Text.status", "Statut")
			.add("Text.newVersion", "Une nouvelle version est disponible souhaitez vous la télécharger?");
	
	public static final DefaultLangValue en_US = new DefaultLangValue()
			.add("OptionPane.cancelButtonText", "Cancel")
			.add("OptionPane.noButtonText", "No")
			.add("OptionPane.yesButtonText", "Yes")
			
			.add("Frame.titleErrorText", "Error")
			
			.add("Text.status", "Statuts")
			.add("Text.newVersion", "A new version is available do you want to download it?");
	
	public static final DefaultLangValue ja_JP = new DefaultLangValue()
			.add("OptionPane.cancelButtonText", "キャンセル")
			.add("OptionPane.noButtonText", "番号")
			.add("OptionPane.yesButtonText", "はい")
			
			.add("Frame.titleErrorText", "エラー")
			
			.add("Text.status", "状態")
			.add("Text.newVersion", "新しいバージョンが利用可能です ダウンロードしますか?");
	
	private List<DoubleStock<String, String>> elements = new ArrayList<DoubleStock<String,String>>();
	
	public DefaultLangValue add(String name, String value) {
		elements.add(new DoubleStock<String, String>(name, value));
		return this;
	}
	
	public void apply() {
		for(int i = 0; i < elements.size(); i++)
			UIManager.put(elements.get(i).getFirst(), elements.get(i).getSecond());
	}
	
	public static void applyDefault() {
		String lang = System.getProperty("user.language") + "_" + System.getProperty("user.country");
		
		LangType type = LangType.getLangType(lang);
		if(type != null)
			type.getDefaultLangValue().apply();
	}
}
