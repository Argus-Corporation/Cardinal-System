package net.argus.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import net.argus.file.FileManager;

public class FontManager {

	public static final Font ARGUS_UI = loadFont(new File(FileManager.getPathInJar("res/font/ARGUS-UI.ttf")), 0, 25);
	public static final Font SF_UI = loadFont(new File(FileManager.getPathInJar("res/font/SF-UI.otf")), 0, 15);
	
	private Font font;
	
	private File path;
	private int style = 0;
	private float size = 15;
	
	public FontManager(File path) {
		this.path = path;
	}
	
	public FontManager(String path) {
		this(new File(path));
	}
		
	public static Font loadFont(File file, int style, float size) {
		Font font = loadFont(file);
		font = font.deriveFont(style, size);
		
		return font;
	}
	
	public static Font loadFont(File file) {
		Font font = null;
		try {font = Font.createFont(Font.TRUETYPE_FONT, file);}
		catch(FontFormatException | IOException e) {e.printStackTrace();}
		
		return font;
	}
	
	public Font getFont() {
		if(font == null)
			font = loadFont(path, style, size);
		
		return font;
	}
	
	public void setStyle(FontStyle style) {
		this.style = style.getId();
	}
	
}
