package net.argus.system;

import javax.swing.UIManager;

import net.argus.gui.Look;

public class InitializedUI implements InitializedSystemManager {

	@Override
	public void preInit(String[] args) {
		Look.chageLook(UIManager.getSystemLookAndFeelClassName());
	}

	@Override
	public void init(String[] args) {
		
	}

	@Override
	public void postInit(String[] args) {
		
	}

}
