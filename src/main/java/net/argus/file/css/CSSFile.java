package net.argus.file.css;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import net.argus.file.CardinalFile;
import net.argus.file.Filter;
import net.argus.instance.Instance;
import net.argus.util.ArrayManager;
import net.argus.util.FontStyle;
import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class CSSFile extends CardinalFile {
	
	public static final String EXTENTION = "css";
	
	public static final Filter CSS_FILTER = new Filter(EXTENTION, "CSS File");
	
	private String fileComplied;
	private String[] words;
	
	public CSSFile(String fileName, String rep) {
		super(fileName, EXTENTION, rep);
		fileComplied = compile(toArray());
	}
	
	public CSSFile(String fileName, String rep, Instance instance) {
		super(fileName, EXTENTION, rep, instance);
		fileComplied = compile(toArray());
	}
	
	public CSSFile(File path) {
		super(path);
		fileComplied = compile(toArray());
	}
	
	public String compile(String[] file) {
		boolean valid = false;
		String lines = "";
		
		for(String str : file)
			lines += str;

		char[] charLines = ArrayManager.remove(lines.toCharArray(), ' ');
		charLines = ArrayManager.remove(charLines, '\t');
		
		charLines = ArrayManager.add(charLines, ' ', charLines.length);
		
		while(!valid) {
			valid = true;
			for(int i = 0; i < charLines.length; i++) {
				if(charLines[i] == '{' && charLines[i - 1] != ' ') {
					charLines = ArrayManager.add(charLines, ' ', i);
					valid = false;
				}
				if(charLines[i] == '{' && charLines[i + 1] != ' ') {
					charLines = ArrayManager.add(charLines, ' ', i + 1);
					valid = false;
				}
				
				if(charLines[i] == '}' && charLines[i - 1] != ' ') {
					charLines = ArrayManager.add(charLines, ' ', i);
					valid = false;
				}
				if(charLines[i] == '}' && charLines[i + 1] != ' ') {
					charLines = ArrayManager.add(charLines, ' ', i + 1);
					valid = false;
				}
				
				if(charLines[i] == ',' && charLines[i + 1] != ' ') {
					charLines = ArrayManager.add(charLines, ' ', i + 1);
					valid = false;
				}
				if(charLines[i] == ',' && charLines[i - 1] != ' ') {
					charLines = ArrayManager.add(charLines, ' ', i);
					valid = false;
				}
				
				if(charLines[i] == ':' && charLines[i + 1] != ' ') {
					charLines = ArrayManager.add(charLines, ' ', i + 1);
					valid = false;					
				}
				
				if(charLines[i] ==';' && charLines[i + 1] != ' ') {
					charLines = ArrayManager.add(charLines, ' ', i + 1);
					valid = false;
				}
			}
		}
		String line = new String(charLines);
		words = line.split(" ");
		
		return line;
	}
	
	public void execut() {
		List<String> objectTypes = new ArrayList<String>();
		List<Object> object = new ArrayList<Object>();
		List<Object> key = new ArrayList<Object>();
		String type = "";
		boolean open = false;
		
		ThreadManager.UPDATE_UI.setTemporaryName();
		
		for(int i = 0; i < words.length; i++) {
			String value = words[i];
			
			if(value.contains("{")) open = true;
			if(value.contains("}")) {objectTypes.clear(); open = false;}
			
			if(open) {
				if(value.contains(":")) {
					key.add(value.split("-")[0]);
					type = value.split("-")[1];
					type = type.substring(0, type.indexOf(':'));
				}
				
				if(value.contains(";")) {
					if(type.equals("color")) {
						object.add(new Color(Integer.parseInt(value.substring(0, value.indexOf(';')), 16)));
					}
					if(type.equals("family")) {
						for(int j = 0; j < objectTypes.size(); j++) {
							Font old = UIManager.getFont(objectTypes.get(j) + ".font");

							if(old != null)
								object.add(new Font(value.substring(0, value.indexOf(';')), old.getStyle(), old.getSize()));
							else
								object.add(new Font(value.substring(0, value.indexOf(';')), 0, 10));
						}
					}
					if(type.equals("style")) {
						for(int j = 0; j < objectTypes.size(); j++) {
							Font old = UIManager.getFont(objectTypes.get(j) + ".font");
							if(old != null)
								object.add(old.deriveFont(FontStyle.getStyle(value.substring(0, value.indexOf(';'))).getId()));
							else
								object.add(new Font("default", FontStyle.getStyle(value.substring(0, value.indexOf(';'))).getId(), 10));
								
						}
					}
					if(type.equals("size")) {
						for(int j = 0; j < objectTypes.size(); j++) {
							//objectTypes.set(j, StringManager.remove(objectTypes.get(j), '\n'));
							Font old = UIManager.getFont(objectTypes.get(j) + ".font");
							if(old != null)
								object.add(UIManager.getFont(objectTypes.get(j) + ".font").deriveFont(Float.valueOf(value.substring(0, value.indexOf(';')))));
							else
								object.add(new Font("default", 0, Integer.valueOf(value.substring(0, value.indexOf(';')))));
						}
					}
					
					for(int j = 0; j < objectTypes.size(); j++) {
						for(int k = 0; k < key.size(); k++) {
							for(int l = 0; l < object.size(); l++) {
								UIManager.put(objectTypes.get(j) + "." + key.get(k), object.get(l));
								Debug.log("Update \"" + objectTypes.get(j) + "." + key.get(k) + "\" key in UIManager");
							}	
						}
					}
					key.clear();
					object.clear();
				}
			}else {
				try {if(words[i + 1].contains("{") || words[i + 1].contains(",")) objectTypes.add(value);
				}catch(ArrayIndexOutOfBoundsException e) {}
			}
		}
		ThreadManager.UPDATE_UI.restorOldParameter();
		
	}
	
	public String getFileCompiled() {return fileComplied;}
	
}
