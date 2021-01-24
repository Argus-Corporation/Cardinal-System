package net.argus.system;

import java.awt.Color;

import javax.swing.UIManager;

import net.argus.gui.Look;

public class InitializedUI implements InitializedSystemManager {

	@Override
	public void preInit(String[] args) {
		Look.chageLook(UIManager.getSystemLookAndFeelClassName());
	}

	@Override
	public void init(String[] args) {
		UIManager.put("Panel.background", Color.WHITE);
		UIManager.put("OptionPane.background", Color.WHITE);
		//UIManager.put("File", value)
	}

	@Override
	public void postInit(String[] args) {
		
	}

}
