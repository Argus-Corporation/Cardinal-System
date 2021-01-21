package net.argus.chat.client.gui.config.profile;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import net.argus.chat.client.gui.HostInfo;
import net.argus.chat.client.gui.config.ConfigManager;
import net.argus.file.Properties;
import net.argus.gui.Button;
import net.argus.gui.ComboBox;
import net.argus.gui.Panel;
import net.argus.gui.TextField;

public class ProfileConfig extends ConfigManager {
	
	private DefaultComboBoxModel<Profile> listModel;
	private ComboBox<Profile> list;
	
	private TextField name;
	private TextField ip;

	public ProfileConfig() {
		super(2);
		listModel = new DefaultComboBoxModel<Profile>();
	}

	@Override
	public Panel getConfigPanel() {
		Panel pan = new Panel();
		pan.setLayout(new BorderLayout());
		
		Panel listPan = getListPanel();
		Panel centerPanel = getCenterPanel();
		Panel buttonPan = getButtonPanel();
		
		load();
		
		pan.add(BorderLayout.NORTH, listPan);
		pan.add(BorderLayout.CENTER, centerPanel);
		pan.add(BorderLayout.SOUTH, buttonPan);
		
		return pan;
	}
	
	public Panel getListPanel() {
		Panel pan = new Panel();
		
		list = new ComboBox<Profile>(listModel);
		list.addActionListener(getListActionListener());
		
		pan.add(list);
		
		return pan;
	}
	
	public Panel getCenterPanel() {
		Panel pan = new Panel();
		
		name = new TextField(10);
		ip = new TextField(10);
		
		name.addKeyListener(getNameKeyListener());
		pan.add(name);
		pan.add(ip);
		
		return pan;
	}
	
	public Panel getButtonPanel() {
		Panel pan = new Panel();
		
		Button remove = new Button("remove");
		Button create = new Button("create");
		Button apply = new Button("apply");
		
		remove.addActionListener(getRemoveActionListener());
		create.addActionListener(getCreateActionListener());
		apply.addActionListener(getApplyActionListener());
		
		pan.add(remove);
		pan.add(create);
		pan.add(apply);
		
		return pan;
	}
	
	public void load() {
		Properties profile = HostInfo.getProfileConfig();
		
		String name;
		String ip;
		
		listModel.removeAllElements();
		for(int i = 0; i < profile.getNumberLine() / 2; i++) {
			name = profile.getString("profile" + i + ".name"); 
			ip = profile.getString("profile" + i + ".ip"); 
			
			listModel.addElement(new Profile(name, ip));
		}
			
	}
	
	private ActionListener getRemoveActionListener() {
		return e -> {
			int index = list.getSelectedIndex();
			
			listModel.removeElementAt(index);
			updateFile();
		};
	}
	
	private ActionListener getCreateActionListener() {
		return e -> {
			listModel.addElement(new Profile("", ""));
			list.setSelectedIndex(listModel.getSize() - 1);
		};
	}
	
	private ActionListener getApplyActionListener() {return e -> apply();}
	
	private ActionListener getListActionListener() {
		return e -> {
			int index = list.getSelectedIndex();
			
			Profile nameText = (Profile) list.getSelectedItem();
			
			name.setText(nameText!=null?nameText.toString():"");
			ip.setText(HostInfo.getProfileConfig().getString("profile" + index + ".ip"));
		};
	}
	
	private KeyListener getNameKeyListener() {
		return new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {keyPressed(e);}
			public void keyPressed(KeyEvent e) {
				Profile nameText = (Profile) list.getSelectedItem();
				nameText.setName(name.getText());
				list.updateUI();
			}
		};
	}
	
	public void add(int index) {
		Properties profileConfig = HostInfo.getProfileConfig();
		try {
			profileConfig.addKey("profile" + index + ".name", name.getText());
			profileConfig.addKey("profile" + index + ".ip", ip.getText());
		}catch(IOException e) {e.printStackTrace();}
	}
	
	public void change(int index) {
		Properties profileConfig = HostInfo.getProfileConfig();
		try {
			profileConfig.setKey("profile" + index + ".name", name.getText());
			profileConfig.setKey("profile" + index + ".ip", ip.getText());
		}catch(IOException e) {e.printStackTrace();}
	}
	
	public void updateFile() {
		Properties profileConfig = HostInfo.getProfileConfig();
		
		List<String> keys = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for(int i = 0; i < listModel.getSize(); i++) {
			keys.add("profile" + i + ".name");
			keys.add("profile" + i + ".ip");
			
			values.add(listModel.getElementAt(i).getName());
			values.add(listModel.getElementAt(i).getIp());
		}
		try {
			profileConfig.clear();
			profileConfig.addKeys(keys, values);
		}catch(IOException e) {}
	}

	@Override
	public void apply() {
		int index = list.getSelectedIndex();
		
		if(index > -1 && index < (HostInfo.getProfileConfig().getNumberLine() / 2))
			change(index);
		else if(index > -1)
			add(index);
	}
	
}