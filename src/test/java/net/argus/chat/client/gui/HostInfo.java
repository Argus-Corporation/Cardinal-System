package net.argus.chat.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import net.argus.chat.client.MainClient;
import net.argus.file.Properties;
import net.argus.gui.Button;
import net.argus.gui.Icon;
import net.argus.gui.Panel;

public class HostInfo {
	
	private static Properties profileConfig = new Properties("profile", "/");
	
	private static String result;
	
	public static void openInfoDialog(Component parent) {
		List<String> hostName = getHostName();
		
		result = null;
		
		JDialog dial = new JDialog();
		dial.setSize(300, 150);
		dial.setLocationRelativeTo(parent);
		dial.setAlwaysOnTop(true);
		dial.setResizable(false);
		dial.setIconImage(Icon.getIcon(MainClient.icon16).getImage());
		dial.setLayout(new BorderLayout());
			
		Panel north = new Panel();
		Panel south = new Panel();
		Panel option = new Panel();
		
		JCheckBox saveCheck = new JCheckBox("IP register");
		north.add(saveCheck);
		
		JCheckBox newCheck = new JCheckBox("new IP");
		south.add(newCheck);
		
		JComboBox<String> list = new JComboBox<String>((String[]) hostName.toArray(new String[hostName.size()]));
		north.add(list);
		
		JTextField host = new JTextField(12);
		south.add(host);
		
		Button ok = new Button("   OK   ", false);
		option.add(ok);
		
		Button cancel = new Button("Cancel", false);
		option.add(cancel);
		
		saveCheck.setSelected(true);
		host.setEnabled(false);
		
		saveCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveCheck.setSelected(true);
				newCheck.setSelected(false);
				
				list.setEnabled(true);
				host.setEnabled(false);
			}
		});
		
		newCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newCheck.setSelected(true);
				saveCheck.setSelected(false);
				
				list.setEnabled(false);
				host.setEnabled(true);
			}
		});
		
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saveCheck.isSelected()) {
					
					dial.setVisible(false);
					result = profileConfig.getString("profile" + hostName.indexOf(list.getSelectedItem()) + ".ip");
				}else {
					dial.setVisible(false);
					result = host.getText();
				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dial.setVisible(false);
			}
		});
		
		dial.add(BorderLayout.NORTH, north);
		dial.add(BorderLayout.CENTER, south);
		dial.add(BorderLayout.SOUTH, option);
		
		dial.pack();
				
		dial.setVisible(true);
		
	}
	
	public static List<String> getHostName() {
		List<String> name = new ArrayList<String>();
		
		for(int i = 0; i < (profileConfig.getNumberLine() / 2); i++)
			name.add(profileConfig.getString("profile" + i + ".name"));
			
		return name;
	}
	
	public static String getHost() {return result;}
	
	public static Properties getProfileConfig() {return profileConfig;}

}
