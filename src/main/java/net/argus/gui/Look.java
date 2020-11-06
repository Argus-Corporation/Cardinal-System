package net.argus.gui;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

public class Look {
	
	public static void chageLook(LookAndFeel laf) {try{UIManager.setLookAndFeel(laf);}catch(Exception e) {e.printStackTrace();}}
	public static void chageLook(String className) {try {UIManager.setLookAndFeel(className);}catch (Exception e){e.printStackTrace();}}

}
