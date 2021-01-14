package net.argus.chat.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import net.argus.gui.Menu;
import net.argus.gui.MenuCheckBoxItem;
import net.argus.gui.MenuItem;
import net.argus.lang.Lang;
import net.argus.lang.LangType;

public class MenuBarClient {

	private Menu connection, encrypting, lang;
	private MenuItem fast, join, leave;
	private MenuCheckBoxItem encrypt;
	
	public JMenuBar getMenuBar() {
		JMenuBar bar = new JMenuBar();
		
		connection = new Menu("connection");
		encrypting = new Menu("encrypting");
		lang = new Menu("lang");
		
		fast = new MenuItem("fast");
		join = new MenuItem("join");
		leave = new MenuItem("leave");
		
		encrypt = new MenuCheckBoxItem("encrypt");
		encrypt.setSelected(GUIClient.config.getBoolean("encrypt"));
		encrypt.addActionListener(getEncryptListener());
		
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
		encrypting.add(encrypt);
		lang.add(change);
		
		bar.add(connection);
		bar.add(encrypting);
		bar.add(lang);
		
		return bar;
	}
	
	public boolean isEncrypt() {
		return encrypt.isSelected();
	}
	
	private ActionListener getEncryptListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {GUIClient.config.setKey("encrypt", encrypt.isSelected());}
				catch(IOException e1) {e1.printStackTrace();}
			}
		};
	}
	
	private ActionListener getChangeLangListener(String langName) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LangType lang = LangType.getLangType(langName, 0);
				Lang.updateLang(lang);
				try {
					GUIClient.config.setKey("lang", lang.getName());
				}catch(IOException e1) {}
			}
		};
	}
	
	public Menu getConnection() {return connection;}
	public Menu getEncrypting() {return encrypting;}
	public Menu getLang() {return lang;}
	
	public MenuItem getFast() {return fast;}
	public MenuItem getJoin() {return join;}
	public MenuItem getLeave() {return leave;}
	
	public MenuCheckBoxItem getEncrypt() {return encrypt;}

}
