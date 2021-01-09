package net.argus.example.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.argus.gui.Menu;
import net.argus.gui.MenuItem;
import net.argus.lang.Lang;
import net.argus.lang.LangType;

public class MenuBarClient {
	
	private MenuItem fast, join, leave;
	
	public JMenuBar getMenuBar() {
		JMenuBar bar = new JMenuBar();
		
		Menu connection = new Menu("connection");
		Menu lang = new Menu("lang");
		
		fast = new MenuItem("fast");
		join = new MenuItem("join");
		leave = new MenuItem("leave");
		GUIClient.leave();
		
		Menu change = new Menu("change");
		
		for(String langName : Lang.getAllLangRelName()) {
			JMenuItem item = new JMenuItem(langName);
			change.add(item);
			
			item.addActionListener(getChangeLangListener(langName));
		}
		
		connection.add(fast);
		connection.add(join);
		connection.add(leave);
		lang.add(change);
		
		bar.add(connection);
		bar.add(lang);
		
		return bar;
	}
	
	private ActionListener getChangeLangListener(String langName) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LangType lang = LangType.getLangType(langName, 0);
				Lang.updateLang(lang);
				try {
					GUIClient.getProperties().setKey("lang", lang.getName());
				}catch(IOException e1) {}
			}
		};
	}
	
	public MenuItem getFast() {return fast;}
	public MenuItem getJoin() {return join;}
	public MenuItem getLeave() {return leave;}

}
