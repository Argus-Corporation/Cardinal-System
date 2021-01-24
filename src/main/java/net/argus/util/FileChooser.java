package net.argus.util;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;

import javax.swing.JFileChooser;

import net.argus.file.Filter;

public class FileChooser extends JFileChooser {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -397707869621147635L;
	
	public FileChooser(String path) {
		super(path);
	}
	
	public FileChooser(Filter filter) {
		this();
		addFilter(filter);
	}
	public FileChooser(String path, Filter filter) {
		this(path);
		addFilter(filter);
	}
	
	public FileChooser() {
		this(System.getProperty("user.home"));
	}
	
	public void addFilter(Filter filter) {
		this.setFileFilter(filter);
	}
	
	
	public File showOpenFile(Component parent) throws HeadlessException {
		int value = super.showOpenDialog(parent);
		return value!=APPROVE_OPTION?null:getSelectedFile();
	}
	
	public String showOpen(Component parent) throws HeadlessException {
		File open = showOpenFile(parent);
		return open!=null?open.getPath():null;
	}
	
	public File showSaveFile(Component parent) throws HeadlessException {
		int value = super.showSaveDialog(parent);
	
		return value!=APPROVE_OPTION?null:getSelectedFile();
	}
	
	public String showSave(Component parent) throws HeadlessException{
		File save = showSaveFile(parent);
		return save!=null?save.getPath():null;
	}
	
}
