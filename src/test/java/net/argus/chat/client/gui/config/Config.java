package net.argus.chat.client.gui.config;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import net.argus.chat.client.MainClient;
import net.argus.chat.client.gui.GUIClient;
import net.argus.file.cjson.CJSONFile;
import net.argus.file.cjson.CJSONPareser;
import net.argus.gui.Panel;
import net.argus.gui.tree.CardinalTree;
import net.argus.gui.tree.Tree;
import net.argus.gui.tree.TreeListener;
import net.argus.util.Math;

public class Config {

	private JFrame fen;
	private JSplitPane split;
	
	private JScrollPane scrollPanTree;
	
	private Tree configTree;
	private Panel optionPanel;
	
	private ConfigManager confManager;

	public Config() {
		Color oldBack = UIManager.getColor("Panel.background");
		UIManager.put("Panel.background", new Color(238, 238, 238));
		
		configTree = getDefaultTree();
		initComponent();
		
		UIManager.put("Panel.background", oldBack);
	}

	private void initComponent() {
		fen = new JFrame("Config");
		fen.setResizable(false);
		fen.setSize(810, 360);
		fen.setIconImage(new ImageIcon(MainClient.icon16).getImage());
		fen.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		fen.addWindowListener(getFrameListener());
		
		scrollPanTree = getTree();
		optionPanel = new Panel();

		configTree.addTreeListener(getTreeListener());
		
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPanTree, optionPanel);
		fen.add(split);
	}
	
	private TreeListener getTreeListener() {
		return new TreeListener() {
			public void selectedItemValue(int id) {
				getFrameListener().windowClosing(null);
				
				int firstNum = Math.toIntArray(id)[0];
				confManager = ConfigManager.getConfigManager(firstNum);
				
				optionPanel = confManager.getConfigPanel();
				scrollPanTree.setPreferredSize(scrollPanTree.getSize());
				
				split.setRightComponent(optionPanel);
				split.repaint();
						
			}
		};
	}
	
	private WindowListener getFrameListener() {
		return new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				if(confManager != null)
					confManager.apply();
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		};
	}
	
	private JScrollPane getTree() {
		JScrollPane	scrollPanTree = new JScrollPane(configTree);
		scrollPanTree.setMinimumSize(new Dimension(80, fen.getHeight()));
		scrollPanTree.setPreferredSize(new Dimension(180, fen.getHeight()));
		scrollPanTree.setMaximumSize(new Dimension(280, fen.getHeight()));
		
		return scrollPanTree;
	}

	private Tree getDefaultTree() {
		Tree tree = new Tree(new CardinalTree(CJSONPareser.parse(new CJSONFile("config", "bin")), "Preference"));
		return tree;
	}

	public void show() {
		fen.setLocationRelativeTo(GUIClient.frame);
		fen.setVisible(true);
	}

	public void hide() {
		fen.setVisible(false);
	}

}
