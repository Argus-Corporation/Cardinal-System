package net.argus.util;

import javax.swing.JFileChooser;

public class FileChooser extends JFileChooser {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -397707869621147635L;
	
	public static final int OPEN_DIALOG = 0;
	public static final int SAVE_DIALOG = 1;
	
	private static JFileChooser jfc;
	private static int returnValue;
	
	public static String getPathFile(String path) {
		common(FILES_ONLY, path);
		int returnValue = jfc.showOpenDialog(null);
		return commonReturn(returnValue);
	}
	public static String getPathFile(int mode, String path) {
		common(FILES_ONLY, path);
		commonSwitch(mode);
		return commonReturn(returnValue);
	}
	
	
	public static String getPathFolder(String path) {
		common(DIRECTORIES_ONLY, path);
		int returnValue = jfc.showOpenDialog(null);
		return commonReturn(returnValue);
	}
	public static String getPathFolder(int mode, String path) {
		common(DIRECTORIES_ONLY, path);
		commonSwitch(mode);
		return commonReturn(returnValue);
	}
	
	private static void common(int mode, String path) {
		jfc = new JFileChooser(path);
		jfc.setFileSelectionMode(mode);
	}
	private static void commonSwitch(int mode) {
		switch(mode) {
		case OPEN_DIALOG:
			returnValue = jfc.showOpenDialog(null);
			break;
			
		case SAVE_DIALOG:
			returnValue = jfc.showSaveDialog(null);
			break;

		default:
			returnValue = jfc.showOpenDialog(null);
			break;
		}
	}
	private static String commonReturn(int returnValue) {
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile().getAbsolutePath();
		}
		return null;
	}
	
}
