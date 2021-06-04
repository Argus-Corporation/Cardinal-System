package net.argus.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import net.argus.util.ArrayManager;

public class Filter extends FileFilter {
	
	private List<String> suffixs = new ArrayList<String>();
	private String description;
	
	public Filter(String[] suffixs, String description) {
		this(ArrayManager.convert(suffixs), description);
	}
	
	public Filter(List<String> suffixs, String description) {
		this.suffixs = suffixs;
		this.description = description;
	}
	
	public Filter(String suffix, String description) {
		this(new String[] {suffix}, description);
	}
	
	public boolean belongs(String suffix) {
		for(String suf : suffixs)
			if(suf.toLowerCase().equals(suffix.toLowerCase()))
				return true;
		return false;
	}

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) return true;
		
		String suffix = null;
		String s = f.getName();
		
		int i = s.lastIndexOf('.');
		
		if(i > 0 && i < s.length() - 1)
			suffix = s.substring(i + 1).toLowerCase();
		
		return suffix != null && belongs(suffix);
	}

	@Override
	public String getDescription() {
		return description;
	}

}
